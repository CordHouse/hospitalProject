package com.example.hospitalproject.Service.Email.html;

import lombok.Data;

@Data
public class CreateEmailHtmlSource {

    /**
     * 회원가입 시 이메일 전송 html 코드
     */
    public static String emailHtmlSendSource(String toEmail) {
        return "<html>\n" +
                "<head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "\t<title>이메일 인증</title>\n" +
                "</head>\n" +
                "<body style=\"margin: 0;\n" +
                "\t\t\tpadding: 0;\n" +
                "\t\t\tbackground-color: #f6f9fc;\">\n" +
                "\t<div class=\"container\" \n" +
                "\tstyle=\"width: 100%;\n" +
                "\tpadding: 20px;\n" +
                "\ttext-align: center;\n" +
                "\tbackground-color: #f6f9fc;\n" +
                "\tborder-top: 5px solid #19ce60;\n" +
                "\tborder-bottom: 5px solid #19ce60;\n" +
                "\tmargin: 0 auto;\n" +
                "\tmax-width: 600px; \n" +
                "\t\">\n" +
                "    \n" +
                "\t<div class=\"header\" \n" +
                "\tstyle=\"background-color: #19ce60;\n" +
                "\tcolor: #ffffff;\n" +
                "\tfont-size: 24px;\n" +
                "\tfont-weight: bold;\n" +
                "\tpadding: 20px;\n" +
                "\ttext-align: center;\">\n" +
                "\tFn<span style = \"color : #b7affff5; font-family: 'Montserrat';\">D</span>oc\n" +
                "\t<span \n" +
                "\t\tstyle = \"color : #f6f6f6; font-family: 'Montserrat';\">\n" +
                "\t\t<br/>이메일 인증</span>\n" +
                "\t</div>\n" +
                "\n" +
                "\t\t<div class=\"message\" \n" +
                "\t\tstyle=\"background-color: #fbfffb;\n" +
                "\t\tpadding: 30px;\n" +
                "\t\tfont-size: 16px;\n" +
                "\t\ttext-align: left;\n" +
                "\t\tmargin-top: 20px;\">\n" +
                "\t안녕하세요! " + "<span style=\"color: #000000;\n" +
                "\tfont-size: 20px;\n" +
                "\tfont-weight: bold;\">" +
                "Fn<span style = \"color : #b7affff5; font-family: 'Montserrat';\">D</span>oc</span>" + "입니다. \n" +
                "\t\t\t<p>회원 가입을 위해 이메일 인증을 진행해주세요.</p>\n" +
                "\t\t\t<p>아래 버튼을 클릭하여 인증을 완료해주세요.</p>\n" +
                "\t\t\t<p>버튼을 클릭시 인증이 완료됩니다.</p>\n" +
                "\t\t\t<p>감사합니다.</p>\n" +
                "\t\t\t<a href="+toEmail+" class=\"button\" \n" +
                "\t\t\tstyle=\"display: inline-block;\n" +
                "\t\t\tbackground-color: #19ce60;\n" +
                "\t\t\tcolor: #ffffff;\n" +
                "\t\t\tpadding: 10px 20px;\n" +
                "\t\t\tmargin-top: 20px;\n" +
                "\t\t\tfont-size: 18px;\n" +
                "\t\t\tfont-weight: bold;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t\tborder-radius: 5px;\n" +
                "\t\t\ttext-decoration: none;\">\n" +
                "\t\t\t이메일 인증하기\n" +
                "\t\t\t</a>\n" +
                "\t\t</div>\n" +
                "\t\t<div class=\"footer\" \n" +
                "\t\t\tstyle=\"background-color: #f6f9fc;\n" +
                "\t\t\tcolor: #6c757d;\n" +
                "\t\t\tfont-size: 14px;\n" +
                "\t\t\tpadding: 20px;\n" +
                "\t\t\ttext-align: center; \">\n" +
                "\t\t\t<p>문의사항은 아래 메일과 번호를 통해 문의해주세요</p>\n" +
                "            <p>문의 &#x2709;:FnDoc@gmail.com &#x2706;:010-8643-8661</p>\n" +
                "\t\t\t<p>본 메일은 FnDoc&trade; 에서 전송한 메일입니다</p>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>";
    }

    public static String passwordHtmlSendSource(String username, String password) {
        return "<html>\n" +
                "<head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "\t<title>임시 비밀번호 발급</title>\n" +
                "</head>\n" +
                "<body style=\"margin: 0;\n" +
                "\t\t\tpadding: 0;\n" +
                "\t\t\tbackground-color: #f6f9fc;\">\n" +
                "\t<div class=\"container\" \n" +
                "\tstyle=\"width: 100%;\n" +
                "\tpadding: 20px;\n" +
                "\ttext-align: center;\n" +
                "\tbackground-color: #f6f9fc;\n" +
                "\tborder-top: 5px solid #19ce60;\n" +
                "\tborder-bottom: 5px solid #19ce60;\n" +
                "\tmargin: 0 auto;\n" +
                "\tmax-width: 600px; \n" +
                "\t\">\n" +
                "    \n" +
                "\t<div class=\"header\" \n" +
                "\tstyle=\"background-color: #19ce60;\n" +
                "\tcolor: #ffffff;\n" +
                "\tfont-size: 24px;\n" +
                "\tfont-weight: bold;\n" +
                "\tpadding: 20px;\n" +
                "\ttext-align: center;\">\n" +
                "\tFn<span style = \"color : #b7affff5; font-family: 'Montserrat';\">D</span>oc\n" +
                "\t<span \n" +
                "\t\tstyle = \"color : #f6f6f6; font-family: 'Montserrat';\">\n" +
                "\t\t<br/>임시 비밀번호 발급</span>\n" +
                "\t</div>\n" +
                "\n" +
                "\t\t<div class=\"message\" \n" +
                "\t\tstyle=\"background-color: #fbfffb;\n" +
                "\t\tpadding: 30px;\n" +
                "\t\tfont-size: 16px;\n" +
                "\t\ttext-align: left;\n" +
                "\t\tmargin-top: 20px;\">\n" +
                "\t안녕하세요! " + "<span style=\"color: #000000;\n" +
                "\tfont-size: 20px;\n" +
                "\tfont-weight: bold;\">" +
                "Fn<span style = \"color : #b7affff5; font-family: 'Montserrat';\">D</span>oc</span>" + "입니다. \n" +
                "\t\t\t<p> <b>" + username + "</b> 계정 비밀번호가 임시 비밀번호로 변경되었습니다.</p>\n" +
                "\t\t\t<p>아래의 임시 비밀번호로 로그인하시고, 비밀번호를 변경해주세요.</p>\n" +
                "\t\t\t<p></p>\n" +
                "\t\t\t<p>감사합니다.</p>\n" +
                "\t\t\t<span class=\"button\" \n" +
                "\t\t\tstyle=\"display: inline-block;\n" +
                "\t\t\tbackground-color: #19ce60;\n" +
                "\t\t\tcolor: #ffffff;\n" +
                "\t\t\tpadding: 10px 20px;\n" +
                "\t\t\tmargin-top: 20px;\n" +
                "\t\t\tfont-size: 18px;\n" +
                "\t\t\tfont-weight: bold;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t\tborder-radius: 5px;\n" +
                "\t\t\ttext-decoration: none;\">\n" +
                "\t\t\t임시 비밀번호 : " + password + "\n" +
                "\t\t\t</span>\n" +
                "\t\t</div>\n" +
                "\t\t<div class=\"footer\" \n" +
                "\t\t\tstyle=\"background-color: #f6f9fc;\n" +
                "\t\t\tcolor: #6c757d;\n" +
                "\t\t\tfont-size: 14px;\n" +
                "\t\t\tpadding: 20px;\n" +
                "\t\t\ttext-align: center; \">\n" +
                "\t\t\t<p>문의사항은 아래 메일과 번호를 통해 문의해주세요</p>\n" +
                "            <p>문의 &#x2709;:FnDoc@gmail.com &#x2706;:010-8643-8661</p>\n" +
                "\t\t\t<p>본 메일은 FnDoc&trade; 에서 전송한 메일입니다</p>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>";
    }

}
