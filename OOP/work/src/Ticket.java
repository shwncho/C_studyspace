public class Ticket {
    private static int numTicketsSold = 0;
    private int ticketNum;

    public Ticket(){
        numTicketsSold++;
        ticketNum = numTicketsSold;
    }

    public static int getNumberSold(){
        return numTicketsSold;
    }

    public int getTicketNumber(){ return ticketNum; }

    public String getInfo(){
        return "ticket # " + ticketNum + "; " +
                numTicketsSold + " ticket(s) sold.";
    }

    public static void main(String[] args) {
        System.out.println(Ticket.getNumberSold());

        Ticket t1 = new Ticket();
        System.out.println(Ticket.getNumberSold());

        Ticket t2 = new Ticket();
        System.out.println(Ticket.getNumberSold());

        t2.getInfo();
        t1.getInfo();
    }
}
