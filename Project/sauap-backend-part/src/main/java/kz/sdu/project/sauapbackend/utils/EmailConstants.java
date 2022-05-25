package kz.sdu.project.sauapbackend.utils;

public class EmailConstants {

    public static final String FROM = "sauap.online@gmail.com";
    public static final String PERSONAL = "Sauap - iOS app for charity activities";
    public static final String CONTENT_TYPE_HTML = "text/html; charset=utf-8";
    public static final String SUBJECT_MONTHLY_DONATION = "Monthly donation receipt";
    public static final String SUBJECT_DIRECT_DONATION =  "A receipt for a donation to a fundraiser";

    private EmailConstants() {
    }

    public static String getMonthlyDonateTemplateReceipt(String fullName, String paymentId, String date,
                                                         String foundationName, Double amount) {
        return RECEIPT_MONTHLY_DONATION_CSS + String.format(RECEIPT_MONTHLY_DONATION_HTML, fullName, paymentId, date, foundationName, amount.toString());
    }

    public static String getDirectDonateTemplateReceipt(String paymentId, String date, String fullName,
                                                        String fundraiseTitle, Double amount, String foundation) {
        return "<html><head>" + RECEIPT_DIRECT_PAYMENT_CSS + "</head>\n<body>" + String.format(RECEIPT_DIRECT_DONATION_HTML, foundation, date, fullName, paymentId, fundraiseTitle, amount.toString(), amount) + "</body></html>";
    }

    private static final String RECEIPT_MONTHLY_DONATION_HTML = "\n" +
            "<table class=\"body-wrap\">\n" +
            "    <tbody><tr>\n" +
            "        <td></td>\n" +
            "        <td class=\"container\" width=\"600\">\n" +
            "            <div class=\"content\">\n" +
            "                <table class=\"main tables-width\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "                    <tbody><tr>\n" +
            "                        <td class=\"content-wrap aligncenter\">\n" +
            "                            <table class=\"tables-width\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "                                <tbody><tr>\n" +
            "                                    <td class=\"content-block\" style=\"text-align: center;\">\n" +
            "                                        <h2>Thanks for using SAUAP app</h2>\n" +
            "                                    </td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td class=\"content-block\">\n" +
            "                                        <table  class=\"invoice\">\n" +
            "                                            <tbody><tr>\n" +
            "                                                <td> %1$s <br> #ID %2$s <br> %3$s </td>\n" +
            "                                            </tr>\n" +
            "                                            <tr>\n" +
            "                                                <td>\n" +
            "                                                    <table class=\"invoice-items\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "                                                        <tbody><tr>\n" +
            "                                                            <td>Monthly Donation to the foundation <br> <span style=\"font-weight: bold;\">'%4$s'</span></td>\n" +
            "                                                            <td class=\"alignright\">%5$s tenge</td>\n" +
            "                                                        </tr>\n" +
            "                                                        <tr class=\"total\">\n" +
            "                                                            <td class=\"alignright\" id=\"total_align\">Total</td>\n" +
            "                                                            <td class=\"alignright\">%5$s tenge</td>\n" +
            "                                                        </tr>\n" +
            "                                                    </tbody></table>\n" +
            "                                                </td>\n" +
            "                                            </tr>\n" +
            "                                        </tbody></table>\n" +
            "                                    </td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td class=\"content-block\" style=\"text-align:center;\">\n" +
            "                                        <a href=\"#\">View in browser</a>\n" +
            "                                    </td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td class=\"content-block\" style=\"text-align: center;\">\n" +
            "                                        Company Inc. 1 Abylai Khan, Kaskelen\n" +
            "                                    </td>\n" +
            "                                </tr>\n" +
            "                            </tbody></table>\n" +
            "                        </td>\n" +
            "                    </tr>\n" +
            "                </tbody></table>\n" +
            "                <div class=\"footer\">\n" +
            "                    <table class=\"tables-width\">\n" +
            "                        <tbody><tr>\n" +
            "                            <td class=\"aligncenter content-block\" style=\"text-align: center;\">Questions? Email <a href=\"mailto:sauap.online@gmail.com\">sauap.online@gmail.com</a></td>\n" +
            "                        </tr>\n" +
            "                    </tbody></table>\n" +
            "                </div></div>\n" +
            "        </td>\n" +
            "        <td></td>\n" +
            "    </tr>\n" +
            "</tbody></table>";

    private static final String RECEIPT_DIRECT_DONATION_HTML = "<div class=\"invoice-box\">\n" +
            "\t\t\t<table cellpadding=\"0\" cellspacing=\"0\">\n" +
            "\t\t\t\t<tr class=\"top\">\n" +
            "\t\t\t\t\t<td colspan=\"2\">\n" +
            "\t\t\t\t\t\t<table>\n" +
            "\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t<td class=\"title\">\n" +
            "\t\t\t\t\t\t\t\t\t<img src=\"https://sauap-bucket.s3.ap-southeast-1.amazonaws.com/logo.png\" id=\"logo\" />\n" +
            "\t\t\t\t\t\t\t\t</td>\n" +
            "\n" +
            "\t\t\t\t\t\t\t\t<td>\n" +
            "\t\t\t\t\t\t\t\t\t<span style=\"font-weight: bold;\">Invoice #Foundation:</span> %s <br />\n" +
            "\t\t\t\t\t\t\t\t\t<span style=\"font-weight: bold;\">Created:</span> %s <br/>\n" +
            "\t\t\t\t\t\t\t\t\t<span style=\"font-weight: bold;\">FIO:</span> %s \n" +
            "\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t</td>\n" +
            "\t\t\t\t</tr>\n" +
            "\n" +
            "\t\t\t\t<tr class=\"information\">\n" +
            "\t\t\t\t\t<td colspan=\"2\">\n" +
            "\t\t\t\t\t\t<table>\n" +
            "\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t<td>\n" +
            "\t\t\t\t\t\t\t\t\tAlmaty region, Karasay area.<br />\n" +
            "\t\t\t\t\t\t\t\t\t040900, Kaskelen city,<br />\n" +
            "\t\t\t\t\t\t\t\t\tAbylai khan street, 1/1\n" +
            "\t\t\t\t\t\t\t\t</td>\n" +
            "\n" +
            "\t\t\t\t\t\t\t\t<td>\n" +
            "\t\t\t\t\t\t\t\t\tSauap .<br />\n" +
            "\t\t\t\t\t\t\t\t\tiOS app for charity activities<br />\n" +
            "\t\t\t\t\t\t\t\t\tsauap.online@gmail.com\n" +
            "\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t</td>\n" +
            "\t\t\t\t</tr>\n" +
            "\n" +
            "\t\t\t\t<tr class=\"heading\">\n" +
            "\t\t\t\t\t<td>Payment Method</td>\n" +
            "\n" +
            "\t\t\t\t\t<td>Check #</td>\n" +
            "\t\t\t\t</tr>\n" +
            "\n" +
            "\t\t\t\t<tr class=\"details\">\n" +
            "\t\t\t\t\t<td>Check</td>\n" +
            "\n" +
            "\t\t\t\t\t<td>#Id %s</td>\n" +
            "\t\t\t\t</tr>\n" +
            "\n" +
            "\t\t\t\t<tr class=\"heading\">\n" +
            "\t\t\t\t\t<td>Item</td>\n" +
            "\n" +
            "\t\t\t\t\t<td>Price</td>\n" +
            "\t\t\t\t</tr>\n" +
            "\n" +
            "\t\t\t\t<tr class=\"item\">\n" +
            "\t\t\t\t\t<td>Donation to fundraise <span style=\"font-weight: bold;\">'%s'</span></td>\n" +
            "\n" +
            "\t\t\t\t\t<td><span style=\"font-weight: bold;\">%s tenge</span></td>\n" +
            "\t\t\t\t</tr>\n" +
            "\n" +
            "\t\t\t\t<tr class=\"total\">\n" +
            "\t\t\t\t\t<td></td>\n" +
            "\n" +
            "\t\t\t\t\t<td><span style=\"font-weight: bold;\">Total: %s tenge</span></td>\n" +
            "\t\t\t\t</tr>\n" +
            "\t\t\t</table>\n" +
            "\t\t</div>\n";

    private static final String RECEIPT_MONTHLY_DONATION_CSS = "<head>\n" +
            "    <style type=\"text/css\">\n" +
            "        /* -------------------------------------\n" +
            "            GLOBAL\n" +
            "            A very basic CSS reset\n" +
            "        ------------------------------------- */\n" +
            "        * {\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "            font-family: \"Helvetica Neue\", \"Helvetica\", Helvetica, Arial, sans-serif;\n" +
            "            box-sizing: border-box;\n" +
            "            font-size: 14px;\n" +
            "        }\n" +
            "\n" +
            "        img {\n" +
            "            max-width: 100%;\n" +
            "        }\n" +
            "\n" +
            "        body {\n" +
            "            -webkit-font-smoothing: antialiased;\n" +
            "            -webkit-text-size-adjust: none;\n" +
            "            width: 100% !important;\n" +
            "            height: 100%;\n" +
            "            line-height: 1.6;\n" +
            "        }\n" +
            "\n" +
            "        /* Let's make sure all tables have defaults */\n" +
            "        table td {\n" +
            "            vertical-align: top;\n" +
            "        }\n" +
            "\n" +
            "        /* -------------------------------------\n" +
            "            BODY & CONTAINER\n" +
            "        ------------------------------------- */\n" +
            "        body {\n" +
            "            background-color: #f6f6f6;\n" +
            "        }\n" +
            "\n" +
            "        .body-wrap {\n" +
            "            background-color: #f6f6f6;\n" +
            "            width: 100%;\n" +
            "        }\n" +
            "\n" +
            "        .container {\n" +
            "            display: block !important;\n" +
            "            max-width: 600px !important;\n" +
            "            margin: 0 auto !important;\n" +
            "            /* makes it centered */\n" +
            "            clear: both !important;\n" +
            "        }\n" +
            "\n" +
            "        .content {\n" +
            "            max-width: 600px;\n" +
            "            margin: 0 auto;\n" +
            "            display: block;\n" +
            "            padding: 20px;\n" +
            "        }\n" +
            "\n" +
            "        /* -------------------------------------\n" +
            "            HEADER, FOOTER, MAIN\n" +
            "        ------------------------------------- */\n" +
            "        .main {\n" +
            "            background: #fff;\n" +
            "            border: 1px solid #e9e9e9;\n" +
            "            border-radius: 3px;\n" +
            "        }\n" +
            "\n" +
            "        .content-wrap {\n" +
            "            padding: 20px;\n" +
            "        }\n" +
            "\n" +
            "        .content-block {\n" +
            "            padding: 0 0 20px;\n" +
            "        }\n" +
            "\n" +
            "        .header {\n" +
            "            width: 100%;\n" +
            "            margin-bottom: 20px;\n" +
            "        }\n" +
            "\n" +
            "        .footer {\n" +
            "            width: 100%;\n" +
            "            clear: both;\n" +
            "            color: #999;\n" +
            "            padding: 20px;\n" +
            "        }\n" +
            "        .footer a {\n" +
            "            color: #999;\n" +
            "        }\n" +
            "        .footer p, .footer a, .footer unsubscribe, .footer td {\n" +
            "            font-size: 12px;\n" +
            "        }\n" +
            "\n" +
            "        /* -------------------------------------\n" +
            "            TYPOGRAPHY\n" +
            "        ------------------------------------- */\n" +
            "        h1, h2, h3 {\n" +
            "            font-family: \"Helvetica Neue\", Helvetica, Arial, \"Lucida Grande\", sans-serif;\n" +
            "            color: #000;\n" +
            "            margin: 40px 0 0;\n" +
            "            line-height: 1.2;\n" +
            "            font-weight: 400;\n" +
            "        }\n" +
            "\n" +
            "        h1 {\n" +
            "            font-size: 32px;\n" +
            "            font-weight: 500;\n" +
            "        }\n" +
            "\n" +
            "        h2 {\n" +
            "            font-size: 24px;\n" +
            "        }\n" +
            "\n" +
            "        h3 {\n" +
            "            font-size: 18px;\n" +
            "        }\n" +
            "\n" +
            "        h4 {\n" +
            "            font-size: 14px;\n" +
            "            font-weight: 600;\n" +
            "        }\n" +
            "\n" +
            "        p, ul, ol {\n" +
            "            margin-bottom: 10px;\n" +
            "            font-weight: normal;\n" +
            "        }\n" +
            "        p li, ul li, ol li {\n" +
            "            margin-left: 5px;\n" +
            "            list-style-position: inside;\n" +
            "        }\n" +
            "\n" +
            "        /* -------------------------------------\n" +
            "            LINKS & BUTTONS\n" +
            "        ------------------------------------- */\n" +
            "        a {\n" +
            "            color: #1ab394;\n" +
            "            text-decoration: underline;\n" +
            "        }\n" +
            "\n" +
            "        .btn-primary {\n" +
            "            text-decoration: none;\n" +
            "            color: #FFF;\n" +
            "            background-color: #1ab394;\n" +
            "            border: solid #1ab394;\n" +
            "            border-width: 5px 10px;\n" +
            "            line-height: 2;\n" +
            "            font-weight: bold;\n" +
            "            text-align: center;\n" +
            "            cursor: pointer;\n" +
            "            display: inline-block;\n" +
            "            border-radius: 5px;\n" +
            "            text-transform: capitalize;\n" +
            "        }\n" +
            "\n" +
            "        /* -------------------------------------\n" +
            "            OTHER STYLES THAT MIGHT BE USEFUL\n" +
            "        ------------------------------------- */\n" +
            "        .last {\n" +
            "            margin-bottom: 0;\n" +
            "        }\n" +
            "\n" +
            "        .first {\n" +
            "            margin-top: 0;\n" +
            "        }\n" +
            "\n" +
            "        .aligncenter {\n" +
            "            text-align: center;\n" +
            "        }\n" +
            "\n" +
            "        .alignright {\n" +
            "            text-align: right;\n" +
            "        }\n" +
            "\n" +
            "        .alignleft {\n" +
            "            text-align: left;\n" +
            "        }\n" +
            "\n" +
            "        .clear {\n" +
            "            clear: both;\n" +
            "        }\n" +
            "\n" +
            "        /* -------------------------------------\n" +
            "            ALERTS\n" +
            "            Change the class depending on warning email, good email or bad email\n" +
            "        ------------------------------------- */\n" +
            "        .alert {\n" +
            "            font-size: 16px;\n" +
            "            color: #fff;\n" +
            "            font-weight: 500;\n" +
            "            padding: 20px;\n" +
            "            text-align: center;\n" +
            "            border-radius: 3px 3px 0 0;\n" +
            "        }\n" +
            "        .alert a {\n" +
            "            color: #fff;\n" +
            "            text-decoration: none;\n" +
            "            font-weight: 500;\n" +
            "            font-size: 16px;\n" +
            "        }\n" +
            "        .alert.alert-warning {\n" +
            "            background: #f8ac59;\n" +
            "        }\n" +
            "        .alert.alert-bad {\n" +
            "            background: #ed5565;\n" +
            "        }\n" +
            "        .alert.alert-good {\n" +
            "            background: #1ab394;\n" +
            "        }\n" +
            "\n" +
            "        /* -------------------------------------\n" +
            "            INVOICE\n" +
            "            Styles for the billing table\n" +
            "        ------------------------------------- */\n" +
            "        .invoice {\n" +
            "            margin: 40px auto;\n" +
            "            text-align: left;\n" +
            "            width: 80%;\n" +
            "        }\n" +
            "        .invoice td {\n" +
            "            padding: 5px 0;\n" +
            "        }\n" +
            "        .invoice .invoice-items {\n" +
            "            width: 100%;\n" +
            "        }\n" +
            "        .invoice .invoice-items td {\n" +
            "            border-top: #eee 1px solid;\n" +
            "        }\n" +
            "        .invoice .invoice-items .total td {\n" +
            "            border-top: 2px solid #333;\n" +
            "            border-bottom: 2px solid #333;\n" +
            "            font-weight: 700;\n" +
            "        }\n" +
            "\n" +
            "        /* -------------------------------------\n" +
            "            RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
            "        ------------------------------------- */\n" +
            "        @media only screen and (max-width: 640px) {\n" +
            "            h1, h2, h3, h4 {\n" +
            "                font-weight: 600 !important;\n" +
            "                margin: 20px 0 5px !important;\n" +
            "            }\n" +
            "\n" +
            "            h1 {\n" +
            "                font-size: 22px !important;\n" +
            "            }\n" +
            "\n" +
            "            h2 {\n" +
            "                font-size: 18px !important;\n" +
            "            }\n" +
            "\n" +
            "            h3 {\n" +
            "                font-size: 16px !important;\n" +
            "            }\n" +
            "\n" +
            "            .container {\n" +
            "                width: 100% !important;\n" +
            "            }\n" +
            "\n" +
            "            .content, .content-wrap {\n" +
            "                padding: 10px !important;\n" +
            "            }\n" +
            "\n" +
            "            .invoice {\n" +
            "                width: 100% !important;\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        #total_align {\n" +
            "            width: 80%;\n" +
            "        }\n" +
            "\n" +
            "        .tables-width {\n" +
            "            width: 100%;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head> \n";

    private static final String RECEIPT_DIRECT_PAYMENT_CSS = "<style>\n" +
            "\t\t\t.invoice-box {\n" +
            "\t\t\t\tmax-width: 800px;\n" +
            "\t\t\t\tmargin: auto;\n" +
            "\t\t\t\tpadding: 30px;\n" +
            "\t\t\t\tborder: 1px solid #eee;\n" +
            "\t\t\t\tbox-shadow: 0 0 10px rgba(0, 0, 0, 0.15);\n" +
            "\t\t\t\tfont-size: 12px;\n" +
            "\t\t\t\tline-height: 24px;\n" +
            "\t\t\t\tfont-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\n" +
            "\t\t\t\tcolor: #555;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.invoice-box table {\n" +
            "\t\t\t\twidth: 100%;\n" +
            "\t\t\t\tline-height: inherit;\n" +
            "\t\t\t\ttext-align: left;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.invoice-box table td {\n" +
            "\t\t\t\tpadding: 5px;\n" +
            "\t\t\t\tvertical-align: top;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.invoice-box table tr td:nth-child(2) {\n" +
            "\t\t\t\ttext-align: right;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.invoice-box table tr.top table td {\n" +
            "\t\t\t\tpadding-bottom: 20px;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.invoice-box table tr.top table td.title {\n" +
            "\t\t\t\tfont-size: 45px;\n" +
            "\t\t\t\tline-height: 45px;\n" +
            "\t\t\t\tcolor: #333;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.invoice-box table tr.information table td {\n" +
            "\t\t\t\tpadding-bottom: 40px;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.invoice-box table tr.heading td {\n" +
            "\t\t\t\tbackground: #eee;\n" +
            "\t\t\t\tborder-bottom: 1px solid #ddd;\n" +
            "\t\t\t\tfont-weight: bold;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.invoice-box table tr.details td {\n" +
            "\t\t\t\tpadding-bottom: 20px;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.invoice-box table tr.item td {\n" +
            "\t\t\t\tborder-bottom: 1px solid #eee;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.invoice-box table tr.item.last td {\n" +
            "\t\t\t\tborder-bottom: none;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.invoice-box table tr.total td:nth-child(2) {\n" +
            "\t\t\t\tborder-top: 2px solid #eee;\n" +
            "\t\t\t\tfont-weight: bold;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t@media only screen and (max-width: 600px) {\n" +
            "\t\t\t\t.invoice-box table tr.top table td {\n" +
            "\t\t\t\t\twidth: 100%;\n" +
            "\t\t\t\t\tdisplay: block;\n" +
            "\t\t\t\t\ttext-align: center;\n" +
            "\t\t\t\t}\n" +
            "\n" +
            "\t\t\t\t.invoice-box table tr.information table td {\n" +
            "\t\t\t\t\twidth: 100%;\n" +
            "\t\t\t\t\tdisplay: block;\n" +
            "\t\t\t\t\ttext-align: center;\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t/** RTL **/\n" +
            "\t\t\t.invoice-box.rtl {\n" +
            "\t\t\t\tdirection: rtl;\n" +
            "\t\t\t\tfont-family: Tahoma, 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.invoice-box.rtl table {\n" +
            "\t\t\t\ttext-align: right;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.invoice-box.rtl table tr td:nth-child(2) {\n" +
            "\t\t\t\ttext-align: left;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t#logo {\n" +
            "\t\t\t\twidth: 100%; \n" +
            "\t\t\t\tmax-width: 200px;\n" +
            "\t\t\t}\n" +
            "\t\t</style>\n";
}
