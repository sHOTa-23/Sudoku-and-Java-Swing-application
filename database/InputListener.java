
/*
this interface is used to communicate in between classes
 */
public interface InputListener {
    void fireAddButton(City city);
    void fireSearchButton(City city, int populationOption, boolean matchOption);
    void fireGetAllButton();
    void fireDeleteButton(City city);
    void fireConnectionCLose();
}
