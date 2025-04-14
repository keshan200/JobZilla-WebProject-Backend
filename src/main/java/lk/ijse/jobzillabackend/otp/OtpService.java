package lk.ijse.jobzillabackend.otp;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OtpService {

    private final Map<String, String> otpStore = new HashMap<>();
    private final Map<String, Long> otpTimestamps = new HashMap<>();
    private static final long OTP_VALIDITY_DURATION = 5 * 60 * 1000; // 5 minutes

    public String generateOtp(String email) {
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000); // Generate 6-digit OTP
        otpStore.put(email, otp);
        otpTimestamps.put(email, System.currentTimeMillis());
        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        if (!otpStore.containsKey(email)) {
            return false;
        }
        String storedOtp = otpStore.get(email);
        long timestamp = otpTimestamps.get(email);

        // Check if OTP has expired
        if (System.currentTimeMillis() - timestamp > OTP_VALIDITY_DURATION) {
            otpStore.remove(email);
            otpTimestamps.remove(email);
            return false;
        }

        return storedOtp.equals(otp);
    }

    public void removeOtp(String email) {
        otpStore.remove(email);
        otpTimestamps.remove(email);
    }
}
