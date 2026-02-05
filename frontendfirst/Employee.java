public class Employee {
    String name;
    int marks;
    String dept;

    public Employee(String name, int marks, String dept) {
        this.name = name;
        this.marks = marks;
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public int getMarks() {
        return marks;
    }

    public String getDept() {
        return dept;
    }

    @Override
    public String toString() {
        return "Employee{name='" + name + "', marks=" + marks + ", dept='" + dept + "'}";
    }
}
