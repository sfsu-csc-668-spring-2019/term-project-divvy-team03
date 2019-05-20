package com.example.divvy.models;

public class User {
        private int userId;
        private String userFirstName;
        private String userLastName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;
        private String fullName;
        private String userCity;
        private String emailAddress;
        private String image;
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
        public void setUserFirstName(String userFirstName){
            this.userFirstName = userFirstName;
        }
        public void setUserLastName(String userLastName){
            this.userLastName = userLastName;
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
        public String getImage() {return this.image;}
        public void setImage(String image) {this.image = image;}
        public void setUserCity(String cityName){
            this.fullName = cityName;
        }
        public String getUserCity(){
            if(userCity!=null){
                return this.userCity;

            }
            return "";
        }
        public void setUserEmail(String newEmailAddress){
            this.emailAddress = newEmailAddress;
        }
        

}
