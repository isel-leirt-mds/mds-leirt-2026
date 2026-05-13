package pt.isel.mds.data_with_optionals;


import pt.isel.mds.data.Car;

import java.time.LocalDate;
import java.util.Optional;

import static pt.isel.mds.utils.Utils.TODO;

public class PersonUtils {

    public static Optional<String> getPersonCarBrand(Person person) {
//        var personCar = person.getCar();
//        if (personCar.isPresent() ) {
//            return Optional.of(personCar.get().getBrand());
//        }
//        return Optional.empty();

        return  person.getCar()
                .map(c -> c.getBrand());

    }
    
    public static Optional<LocalDate> getPersonCarInsuranceDate(Person person) {
//        TODO("getPersonCarInsuranceDate(Person person)");
//        return Optional.empty();

        return person.getCar()
                .flatMap(c -> c.getInsurance())
                .map(ins -> ins.getExpirationDate());
    }

}
