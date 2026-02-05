import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

public class Demo {
    public static void main(String[] args) {
        List<Employee> employees = List.of(
            new Employee("Alice", 30, "Engineering"),
            new Employee("Bob", 25, "Marketing"),
            new Employee("Charlie", 35, "Engineering"),
            new Employee("Diana", 28, "HR")
        );
        List<Employee> filteredList = new ArrayList<>();

        employees.forEach(emp -> {
            if(emp.getMarks() >= 30) {
                filteredList.add(emp);
            }
        });

        Collections.sort(filteredList, (e1, e2) -> e1.getName().compareTo(e2.getName()));

        filteredList.forEach(System.out::println);
    }
}