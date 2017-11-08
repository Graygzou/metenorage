package Engine.Logic;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class TestComponent implements LogicComponent {
    @Override
    public void Update() {
        System.out.println("Hello from TestComponent! :)");
    }
}
