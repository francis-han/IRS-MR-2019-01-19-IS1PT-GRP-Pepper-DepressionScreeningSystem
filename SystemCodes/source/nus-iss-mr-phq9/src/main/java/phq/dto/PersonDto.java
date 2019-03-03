package phq.dto;

public class PersonDto {
    private String gender = "0";
    private String age = "0";
    private String ethnicity = "0";
    private String maritalStatus = "0";
    private String chronicPhysicalCdn = "0";

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getChronicPhysicalCdn() {
        return chronicPhysicalCdn;
    }

    public void setChronicPhysicalCdn(String chronicPhysicalCdn) {
        this.chronicPhysicalCdn = chronicPhysicalCdn;
    }

    public boolean isLowRisk() {
        return age.equals("3");
    }
}
