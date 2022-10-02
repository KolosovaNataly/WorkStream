import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;




public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }


        //Найти количество несовершеннолетних
        long counts = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println(counts);

        //Получить список фамилий призывников
        List<String> surname = persons.stream()
                .filter(male -> male.getSex() == Sex.MAN)
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        surname.forEach(System.out::println);

        //Список потенциально работоспособных людей с высшим образованием
        Predicate<Person> predicate = person -> {
            Sex sex = person.getSex();
            int age = person.getAge();
            return (person.getEducation() == Education.HIGHER) &&
                    ((sex == Sex.MAN && (age >= 18 && age < 65)) ||
                            (sex == Sex.WOMAN && (age >= 18 && age < 60)));
        };
        Collection<Person> workers = persons.stream()
                .filter(predicate)
                .sorted(Comparator.comparing(Person::getFamily).thenComparing(Person::getAge))
                .collect(Collectors.toList());
        workers.forEach(System.out::println);

    }
}
