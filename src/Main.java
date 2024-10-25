public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        Task task = new Task("Проект", "Сделать проект", Status.NEW);

        System.out.println(task.hashCode());
        task.setId(task.hashCode());
        System.out.println(task.getId());

    }
}
