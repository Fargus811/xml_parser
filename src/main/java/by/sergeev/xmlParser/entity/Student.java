package by.sergeev.xmlParser.entity;

public class Student {
    private String login;
    private String name;
    private String faculty;
    private long telephone;
    private Address address = new Address();

    public Student() {
    }

    public Student(String login, String name, String faculty, long telephone, Address address) {
        this.login = login;
        this.name = name;
        this.faculty = faculty;
        this.telephone = telephone;
        this.address = address;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public long getTelephone() {
        return telephone;
    }

    public void setTelephone(long telephone) {
        this.telephone = telephone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;

        Student student = (Student) o;

        if (telephone != student.telephone) return false;
        if (login != null ? !login.equals(student.login) : student.login != null) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        if (faculty != null ? !faculty.equals(student.faculty) : student.faculty != null) return false;
        return address != null ? address.equals(student.address) : student.address == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (faculty != null ? faculty.hashCode() : 0);
        result = 31 * result + (int) (telephone ^ (telephone >>> 32));
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append("login='").append(login).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", faculty='").append(faculty).append('\'');
        sb.append(", telephone=").append(telephone);
        sb.append(", address=").append(address);
        sb.append('}');
        return sb.toString();
    }

    public class Address {
        private String country;
        private String city;
        private String street;

        public Address() {
        }

        public Address(String country, String city, String street) {
            this.country = country;
            this.city = city;
            this.street = street;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Address)) return false;

            Address address = (Address) o;

            if (country != null ? !country.equals(address.country) : address.country != null) return false;
            if (city != null ? !city.equals(address.city) : address.city != null) return false;
            return street != null ? street.equals(address.street) : address.street == null;
        }

        @Override
        public int hashCode() {
            int result = country != null ? country.hashCode() : 0;
            result = 31 * result + (city != null ? city.hashCode() : 0);
            result = 31 * result + (street != null ? street.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Address{");
            sb.append("country='").append(country).append('\'');
            sb.append(", city='").append(city).append('\'');
            sb.append(", street='").append(street).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

}
