package com.scnu.edu.vo;

import lombok.Data;

import java.util.List;

@Data
public class FirstSubject {

    private String id;

    private String title;

    private List<SecondSubject> children;

}
