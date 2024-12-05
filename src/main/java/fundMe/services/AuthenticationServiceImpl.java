package fundMe.services;

import fundMe.data.models.Role;
import fundMe.data.models.User;
import fundMe.data.repositories.UserRepo;
import fundMe.dtos.request.CreateAccountRequest;
import fundMe.dtos.request.LoginRequest;
import fundMe.dtos.response.CreateAccountResponse;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepo userRepo;
    private int counter = 0;

    public AuthenticationServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public CreateAccountResponse Register(CreateAccountRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setNIN(request.getNIN());
        user.setRole(Role.BORROWER);
        user.setLoggedIn(false);
        userRepo.save(user);
        CreateAccountResponse response = new CreateAccountResponse();
        response.setMessage("Successfully Registered!");
        return response;
    }

    @Override
    public boolean login(LoginRequest loginRequest) {
        User user = this.userRepo.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new IllegalArgumentException("Invalid username or password!");
        }
        user.setUsername(loginRequest.getUsername());
        user.setPassword(loginRequest.getPassword());
        user.setLoggedIn(true);
        System.out.println("Logged In Successfully");
        return user.isLoggedIn();
    }

    @Override
    public boolean logout() {
        User user = new User();
        if(!user.isLoggedIn()){
            throw new IllegalArgumentException("Invalid username or password!");
        }
        user.setLoggedIn(false);
        return user.isLoggedIn();
    }

    public int count() {
        return counter++;
    }
}