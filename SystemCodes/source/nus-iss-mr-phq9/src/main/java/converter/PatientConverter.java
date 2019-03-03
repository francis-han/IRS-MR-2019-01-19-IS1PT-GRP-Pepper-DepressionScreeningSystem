package converter;

import com.phq9_final.phq9final.PatientParticular;
import phq.dto.PersonDto;

public class PatientConverter {
    public static PatientParticular convertFromPersonDto(PersonDto personDto) {
        PatientParticular patient = new PatientParticular();
        patient.setAge( getAge(personDto.getAge() ) );
        patient.setGender( getGender(personDto.getGender() ) );
        patient.setRace( getRace(personDto.getEthnicity() ) );
        patient.setMarital_status( getMaritialStatus(personDto.getMaritalStatus() ) );
        patient.setChronic( getChronic(personDto.getChronicPhysicalCdn() ) );

        return patient;
    }

    private static String getAge(String age) {
        if( age.equals("0") ) {
            return "18-34";
        } else if ( age.equals("1") ) {
            return "35-49";
        } else if ( age.equals("2") ) {
            return "50-64";
        }
        return "65+";
    }

    private static String getGender(String gender) {
        if( gender.equals("0") ) {
            return "Male";
        }
        return "Female";
    }

    private static String getRace(String race) {
        if( race.equals("0") ) {
            return "Chinese";
        }else if ( race.equals("1") ) {
            return "Malay";
        } else if ( race.equals("2") ) {
            return "Indian";
        }
        return "Others";
    }

    private static String getMaritialStatus(String status) {
        if( status.equals("0") ) {
            return "Single";
        }else if ( status.equals("1") ) {
            return "Married";
        } else if ( status.equals("2") ) {
            return "Separated";
        }
        return "Widowed";
    }

    private static String getChronic(String status) {
        if( status.equals("0") ) {
            return "Yes";
        }
        return "No";
    }
}
