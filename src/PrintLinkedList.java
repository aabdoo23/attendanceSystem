import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

public class PrintLinkedList implements Printable {
    String []linkedList;
    Employee employee=null;
    Head head=null;

    public PrintLinkedList(Employee employee,String[] linkedList1) {
        linkedList = linkedList1;
        this.employee=employee;
    }
    public PrintLinkedList(Head head,String[] linkedList1) {
        linkedList = linkedList1;
        this.head=head;
    }
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex != 0) {
            return NO_SUCH_PAGE;
        }

        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(Color.BLACK);

        int x = (int) pf.getImageableX();
        int y = (int) pf.getImageableY();
        int width = (int) pf.getImageableWidth();
        int height = (int) pf.getImageableHeight();

        int textX = x + 50;
        int textY = y + 50;

        String tab = "    ";
        if(employee!=null){
            g.drawString(tab + "Employee details: ID: "+employee.getID()+" ,User name: "+ employee.getUserName(), textX, textY);
            FontMetrics fm = g.getFontMetrics();
            textX = x + 50;
            textY += fm.getHeight() + 5;
        }
        else if(head!=null){
            g.drawString(tab + "Head details: ID: "+head.getID()+" ,User name: "+ head.getUserName(), textX, textY);
            FontMetrics fm = g.getFontMetrics();
            textX = x + 50;
            textY += fm.getHeight() + 5;
            g.drawString(tab + "Report:\n", textX, textY);
            fm = g.getFontMetrics();
            textX = x + 50;
            textY += fm.getHeight() + 5;
        }
        g.setFont(new Font("Arial", Font.PLAIN, 12));

        for (String element : linkedList) {
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(tab + element);
            if (textX + textWidth > x + width) {
                textX = x + 50;
                textY += fm.getHeight() + 5;
            }
            g.drawString(tab + element, textX, textY);
            textX += textWidth;
        }

        return PAGE_EXISTS;
    }


}
