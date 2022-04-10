package telran.pulse.monitoring;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import telran.pulse.monitoring.dto.DoctorPatientData;
import telran.pulse.monitoring.dto.SensorJump;


import java.util.function.Consumer;

@SpringBootApplication
@Log4j2
public class JumpsNotifierApplication {

    @Value("${app.mail.subject: Critical Pulse Jump}")
    String subject;

    @Autowired
    JavaMailSender jms;

    @Autowired
    DataProviderClient client;


    public static void main(String[] args) {
        SpringApplication.run(JumpsNotifierApplication.class, args);
    }
    @Bean
    Consumer<SensorJump> criticalJumpsConsumer() {
        return this::jumpsProcessing;
    }

    private void jumpsProcessing(SensorJump sensorJump) {
        log.trace("received sensor id {} previous value {} current value {}",
                sensorJump.sensorId, sensorJump.previousValue, sensorJump.currentValue);
        DoctorPatientData data = client.getData(sensorJump.sensorId);
        log.debug("data received is {}", data);
        sendMail(data, sensorJump);
    }

    private void sendMail(DoctorPatientData data, SensorJump sensorJump) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(data.email);
        message.setSubject(subject);
        message.setText(String.format("Dear %s,\nYour patient %s had the critical pulse jump\n " +
                "Previous pulse value %d; current pulse value %d\n",
                data.doctorName,
                data.patientName,
                sensorJump.previousValue,
                sensorJump.currentValue));
        jms.send(message);
        log.debug("mail send to {}", data.email);
    }
}
