package com.dnipro.beldii.lesson10.model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
      private String id = java.util.UUID.randomUUID().toString();
      private int age = 0;
      private String name;
      private String gender = "";
      private String company = "";
      private String email = "";
      private String photo = "";

      public int getAge() {
            return age;
      }

      public String getId() {
            return id;
      }

      public User(String id, String name) {
            if (!id.isEmpty()) {
                  this.id = id;
            }
            this.name = name;
      }

      public String getName() {
            return name;
      }

      public String getGender() {
            return gender;
      }

      public String getCompany() {
            return company;
      }

      public String getEmail() {
            return email;
      }

      public String getPhoto() {
            return photo;
      }

      public void setAge(int age) {
            this.age = age;
      }

      public void setName(String name) {
            this.name = name;
      }

      public void setGender(String gender) {
            this.gender = gender;
      }

      public void setCompany(String company) {
            this.company = company;
      }

      public void setEmail(String email) {
            this.email = email;
      }

      public void setPhoto(String photo) {
            this.photo = photo;
      }

      @Override
      public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return age == user.age && id.equals(user.id) && name.equals(user.name) &&
                    Objects.equals(gender, user.gender) && Objects.equals(company, user.company) &&
                    Objects.equals(email, user.email) && Objects.equals(photo, user.photo);
      }

      @Override
      public int hashCode() {
            return Objects.hash(id, age, name, gender, company, email, photo);
      }
}
