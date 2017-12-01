package Engine.System.Component.Messaging;

import Engine.System.Component.Component;


/**
 * @author Florian VIDAL <florianvidals@gmail.com>
 */

public class Message<T> {

    private Component sender;
    private Component receiver;
    private String instruction;
    private T data;

    /**
     * Message transmitted between two components.
     *
     * The type T of data is intrinsically tied to the instruction. Both the sender and the receiver should
     * be aware of the type of data according to the instruction.
     *
     * Message is read-only.
     *
     * @param sender      Component from where the message is coming
     * @param receiver    Component which the message is meant for
     * @param instruction Which instruction should be executed by the receiver
     * @param data        The data needed (or not) by the receiver in order to execute the instruction
     */
    public Message(Component sender, Component receiver, String instruction, T data) {
        this.sender = sender;
        this.receiver = receiver;
        this.instruction = instruction;
        this.data = data;
    }

    public Component getSender() {
        return sender;
    }

    public Component getReceiver() {
        return receiver;
    }

    public String getInstruction() {
        return instruction;
    }

    public T getData() {
        return data;
    }
}