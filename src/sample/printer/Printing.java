package sample.printer;

import javafx.print.*;
import sample.User;

import java.io.IOException;
import java.util.List;

public class Printing {

    public static void printOne(User user,Printer printer){
        PageLayout pageLayout = printer.createPageLayout(Paper.A6, PageOrientation.LANDSCAPE, 0,0,0,0);
        PrinterJob job = PrinterJob.createPrinterJob(printer);
        boolean printed;
        try {
            printed = job.printPage(pageLayout,new PrintedBlankA6(user));
        } catch (IOException e) {
            printed = false;
            e.printStackTrace();
        }
        if (printed){
            job.endJob();
        }

    }
    public static void printMore(List<User> users,Printer printer){
        if(users.isEmpty())return;
        PageLayout pageLayout = printer.createPageLayout(Paper.A6, PageOrientation.LANDSCAPE, 0,0,0,0);
        Thread thread = new Thread(() -> {
            for (int i = 0; i < users.size(); i += 10) {
                PrinterJob job = PrinterJob.createPrinterJob(printer);
                boolean printed = true;
                for (int j = i; j < i + 10 && j < users.size(); j++) {
                    for (int k = 0; k < users.get(j).getLeafCount(); k++) {
                        try {
                            printed = job.printPage(pageLayout,new PrintedBlankA6(users.get(j)));
                        } catch (IOException e) {
                            printed = false;
                            e.printStackTrace();
                        }
                    }
                }
                if (printed){
                    job.endJob();
                }
            }
        });
        thread.start();
    }


}
