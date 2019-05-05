package com.example.divvy.models;

public class User {
        private int userId;
        private String userFirstName;
        private String userLastName;
        private String fullName;
        private String userCity;
        private String emailAddress;
        public User(){
          /*
            Default Constructor
           */
        }
        public User(int userID){
            this.userId = userID;
        }

        public String getUserFirstName(){
            if(userFirstName!=null){
                return userFirstName;
            }
            return "";
        }
        public String getUserLastName(){
            if(userLastName!=null){
                return userLastName;
            }
            return userLastName;
        }
        public String getFullName(){
            return getUserFirstName()+" "+getUserLastName();
        }
        public String getEmailAddress(){
            if(emailAddress!=null){
                return emailAddress;
            }
            return "";
        }
        

}
