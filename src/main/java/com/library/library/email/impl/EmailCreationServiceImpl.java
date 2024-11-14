package com.library.library.email.impl;

import com.library.library.member.Member;
import com.library.library.transaction.BorrowingBookTransactionRepository;
import com.library.library.email.EmailCreationService;
import com.library.library.email.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailCreationServiceImpl implements EmailCreationService {

    private final EmailService emailService;
    private final BorrowingBookTransactionRepository borrowingObjectTransactionRepository;

    @Scheduled(cron = "0 15 10 * * ?")
    @Override
    public void createDebtorMail() {
        LocalDate now = LocalDate.now();
        List<Member> debtors = borrowingObjectTransactionRepository.findDebtorsTransactions(now);

        String subject = "It's time to bring back a book!";
        String message = "Bring back a book!";

        debtors.forEach(debtor -> {
            try {
                emailService.sendNotificationEmail(debtor.getEmail(), subject, message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @SneakyThrows
    @Override
    public String createLogInMail(String email) {
        String randomCode = createRandomCode();

        emailService.sendNotificationEmail(
                email,
                "Log in library",
                "Don't tell anyone about the code!\n" + randomCode);

        return randomCode;
    }

    private String createRandomCode() {
        return new Random().ints(97, 122)
                .limit(7)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}