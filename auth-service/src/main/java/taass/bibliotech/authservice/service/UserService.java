package taass.bibliotech.authservice.service;

import taass.bibliotech.authservice.models.AuthenticationRequestGoogle;
import taass.bibliotech.authservice.models.RegistrationRequest;

public interface UserService {

    void signUp(RegistrationRequest registrationRequest) throws Exception;

    void googleSignUp(AuthenticationRequestGoogle authenticationRequest);

    /*AccountProjection updateAccount(long accountId, AccountEditForm form) throws UnexpectedErrorException, ForbiddenOperationException;

    void disableAccount(long accountId, AccountType currentType) throws UnexpectedErrorException;

    AccountProjection updateProfileImage(long accountId, MultipartFile profileImage) throws ResourceNotSupportedException;*/
}

