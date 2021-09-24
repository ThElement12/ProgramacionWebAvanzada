package com.practica1.practica1backend.Services;

import com.practica1.practica1backend.Models.Student;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class Principal {
    private List<Student> students = new ArrayList<>();

    public List<Student> findAll() {
        return students;
    }

    public Student findByEnrollment(int enrrollment) {
        for (Student aux : students) {
            if (aux.getEnrollment() == enrrollment) {
                return aux;
            }
        }
        return null;
    }

    public Boolean existByEnrollment(int enrrollment) {
        for (Student aux : students) {
            if (aux.getEnrollment() == enrrollment) {
                return true;
            }
        }
        return false;
    }

    public void save(Student student) {
        students.add(student);
    }

    public void update(Student student) {
        for (Student aux : students) {
            if (aux.getEnrollment() == student.getEnrollment()) {
                aux.setName(student.getName());
                aux.setLastName(student.getLastName());
                aux.setPhone(student.getPhone());
            }
        }
    }

    public void deleteByEnrrollment(int enrrollment) {
        Student student = findByEnrollment(enrrollment);
        int num = -1;

        for (Student aux : students) {
            num++;
            if (aux.getEnrollment() == student.getEnrollment()) {
                break;
            }
        }
        if(num > -1)
            students.remove(num);

    }


}
