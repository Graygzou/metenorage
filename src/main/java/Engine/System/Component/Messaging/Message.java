package Engine.System.Component.Messaging;

/**
 * @author Florian VIDAL <florianvidals@gmail.com>
 * @author Grégoire Boiron <gregoire.boiron@gmail.com>
 */

public class Message<T> {

    private Integer IDsender;
    private Integer IDreceiver;
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
    public Message(Integer sender, Integer receiver, String instruction, T data) {
        this.IDsender = sender;
        this.IDreceiver = receiver;
        this.instruction = instruction;
        this.data = data;
    }

    public Integer getSender() {
        return IDsender;
    }

    public Integer getReceiver() {
        return IDreceiver;
    }

    public String getInstruction() {
        return instruction;
    }

    public T getData() {
        return data;
    }
}