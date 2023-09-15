package com.example.capstone_3_updated.Service;

import com.example.capstone_3_updated.Api.ApiException;
import com.example.capstone_3_updated.Model.Laboratories;
import com.example.capstone_3_updated.Model.TestType;
import com.example.capstone_3_updated.Model.User;
import com.example.capstone_3_updated.Repository.LaboartoriesRepository;
import com.example.capstone_3_updated.Repository.TestTypeRepository;
import com.example.capstone_3_updated.Repository.UserRepository;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.fasterxml.jackson.databind.ser.impl.UnknownSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestTypeService {
    private final TestTypeRepository testTypeRepository;
    private final LaboartoriesRepository laboartoriesRepository;
    private final UserRepository userRepository;

    public List<TestType> getAllTestType(){
        return testTypeRepository.findAll();
    }

    public void addTestType(Integer laboartory_id,TestType testType){
        Laboratories laboratories = laboartoriesRepository.findLaboratoriesById(laboartory_id);

        if (laboratories == null)
            throw new ApiException("Sorry, the laboartory not found");

        testType.setLaboratory(laboratories);
        testTypeRepository.save(testType);
    }

    public void updateTestType(Integer id, TestType testType){
        TestType oldTestType = testTypeRepository.findTestTypeById(id);

        if (oldTestType ==null)
            throw new ApiException("Sorry the test type not found");

        oldTestType.setName(testType.getName());
        oldTestType.setDescription(testType.getDescription());
        oldTestType.setPrice(testType.getPrice());
        oldTestType.setTest_date(testType.getTest_date());
        testTypeRepository.save(oldTestType);
    }

    public void deleteTestType(Integer id){
        TestType deleteTestType = testTypeRepository.findTestTypeById(id);

        if (deleteTestType == null)
            throw new ApiException("Sorry the test type not found");
        deleteTestType.setUsers(null);
        testTypeRepository.delete(deleteTestType);
    }


//    public void updateBookDate(String testname, Date new_date,Integer user_id,Integer lab_id){
//        User user=userRepository.findUserById(user_id);
//        TestType testcheck=testTypeRepository.findTestsTypeByLaboratoryId(lab_id);
//
//        if(user.getTestType().getAppointment().equals(true) && testcheck.getName().equals(testname))
//            if (new_date != user.getTestType().getTest_date())
//                if (new_date.compareTo(testcheck.getTest_date()) < 0) {
//                    user.setTestType(testcheck);
//                    testTypeRepository.save(testcheck);
//
//                }
//    }



    public void booking_appointment(Integer user_id, Integer testType_id){
        User user = userRepository.findUserById(user_id);
        TestType testType = testTypeRepository.findTestTypeById(testType_id);
        Laboratories laboratory = laboartoriesRepository.findLaboratoriesById(testType.getLaboratory().getId());


        if (user == null)
            throw new ApiException("Sorry, the user id is wrong");
        else if (testType == null || laboratory == null)
            throw new ApiException("Sorry, the test type id is wrong");

        if (testType.getAppointment())
            throw new ApiException("Sorry, this test type already booked");
        else {
            user.setTestType(testType);
            testType.setAppointment(true);
            user.getLaboratories().add(laboratory);
            laboratory.getUsers().add(user);
        }

        testTypeRepository.save(testType);

        userRepository.save(user);
        laboartoriesRepository.save(laboratory);
    }

    public Double nationalDay_Offer(Integer testType_id) throws ParseException {
        TestType testType = testTypeRepository.findTestTypeById(testType_id);

        if (testType == null)
            throw new ApiException("Sorry , the test id is wrong");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //national day format
        Date nationalDate = dateFormat.parse("2023-09-23");

        // remove the portion of hours, min, seconds,
        Date testDate = getZeroTimeDate(testType.getTest_date());

        if (nationalDate.compareTo(testDate) != 0)
            throw new ApiException("Sorry, can't get a discount because the test date is not equal to national day");


        Double discount = testType.getPrice() - (testType.getPrice() * 0.15);

        if (discount < 0)
            throw new ApiException("Sorry the discount wrong");

        testType.setPrice(discount);
        testTypeRepository.save(testType);

        return discount;
    }

    private static Date getZeroTimeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        return date;
    }

    public void changeTestDate(Integer user_id,Integer old_test, Integer new_Test ){
        User user =userRepository.findUserById(user_id);
        TestType oldTest = testTypeRepository.findTestTypeById(old_test);
        TestType newTest = testTypeRepository.findTestTypeById(new_Test);

        if (old_test == null || new_Test ==null){
            throw new ApiException("Sorry , the test id is wrong");
        }else if (oldTest.getId() == newTest.getId()){
            throw new ApiException("Sorry, the old test same as the new test");}

        if (user ==null)
            throw new ApiException("Sorry, the user is is wrong");

        user.setTestType(newTest);
        newTest.setAppointment(true);
        oldTest.setAppointment(false);

        testTypeRepository.save(oldTest);
        testTypeRepository.save(newTest);
        userRepository.save(user);
    }
}
