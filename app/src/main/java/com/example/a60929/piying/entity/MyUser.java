package com.example.a60929.piying.entity;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;


//用户属性
public class MyUser extends BmobUser {
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 国家
     */

    private String country;

    /**
     * 得分数
     */
    private Integer score;


    /**
     * 抢断次数
     */
    private Integer steal;


    /**
     * 犯规次数
     */
    private Integer foul;


    /**
     * 失误个数
     */
    private Integer fault;


    /**
     * 年龄
     */
    private Integer age;


    /**
     * 性别
     */
    private boolean sex;
    private Integer gender;


    /**
     * 用户当前位置
     */
    private BmobGeoPoint address;


    /**
     * 头像
     */
    private BmobFile avatar;


    /**
     * 别名
     */
    private List<String> alias;

    //简介
    private String jianjie;


    public String getNickname() {
        return nickname;
    }

    public MyUser setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public MyUser setCountry(String country) {
        this.country = country;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public MyUser setScore(Integer score) {
        this.score = score;
        return this;
    }

    public Integer getSteal() {
        return steal;
    }

    public MyUser setSteal(Integer steal) {
        this.steal = steal;
        return this;
    }

    public Integer getFoul() {
        return foul;
    }

    public MyUser setFoul(Integer foul) {
        this.foul = foul;
        return this;
    }

    public Integer getFault() {
        return fault;
    }

    public MyUser setFault(Integer fault) {
        this.fault = fault;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public MyUser setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getGender() {
        return gender;
    }

    public MyUser setGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public BmobGeoPoint getAddress() {
        return address;
    }

    public MyUser setAddress(BmobGeoPoint address) {
        this.address = address;
        return this;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public MyUser setAvatar(BmobFile avatar) {
        this.avatar = avatar;
        return this;
    }

    public List<String> getAlias() {
        return alias;
    }

    public MyUser setAlias(List<String> alias) {
        this.alias = alias;
        return this;
    }

    public String getJianjie() {
        return jianjie;
    }

    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

}


