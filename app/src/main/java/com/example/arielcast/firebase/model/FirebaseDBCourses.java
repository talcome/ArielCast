package com.example.arielcast.firebase.model;

import com.example.arielcast.firebase.model.dataObject.CourseObj;
import com.google.firebase.database.DatabaseReference;

public class FirebaseDBCourses extends FirebaseBaseModel
{

    public void addCourseToDB(String courseId,String courseName,String lecturerId,String semester,int year,int credits)
    {
        CourseObj courseReg=new CourseObj(courseId,courseName,lecturerId,semester,year,credits);
        myRef.child("courses").child(courseId).setValue(courseReg);
    }

    public DatabaseReference getCourseFromDB(String courseID)
    {
        return myRef.child("courses").child(courseID);
    }

    public void WriteSearchCourse(String courseId,String courseName,String lecturerId,String semester,int year,int credits)
    {
        CourseObj course = new CourseObj(courseId, courseName, lecturerId, semester, year, credits);
        course.setCourseId(courseId);
        myRef.child("searchCourse").child(course.getCourseName()).child(courseId).setValue(course);
    }


}
