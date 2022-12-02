/*
 * package com.capeelectric.controller;
 * 
 * import static org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.junit.jupiter.api.Assertions.assertNotNull; import static
 * org.mockito.Mockito.when;
 * 
 * import java.io.IOException; import java.net.URISyntaxException;
 * 
 * import javax.mail.MessagingException;
 * 
 * import org.junit.jupiter.api.Test; import
 * org.junit.jupiter.api.extension.ExtendWith; import org.mockito.InjectMocks;
 * import org.mockito.junit.jupiter.MockitoExtension; import
 * org.springframework.boot.test.mock.mockito.MockBean; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.mock.web.MockHttpServletRequest; import
 * org.springframework.security.authentication.AuthenticationManager; import
 * org.springframework.security.authentication.
 * UsernamePasswordAuthenticationToken; import
 * org.springframework.test.context.junit.jupiter.SpringExtension; import
 * org.springframework.web.context.request.RequestContextHolder; import
 * org.springframework.web.context.request.ServletRequestAttributes;
 * 
 * import com.capeelectric.config.JwtTokenUtil; import
 * com.capeelectric.exception.ChangePasswordException; import
 * com.capeelectric.exception.ForgotPasswordException; import
 * com.capeelectric.exception.UpdatePasswordException; import
 * com.capeelectric.exception.UserException; import
 * com.capeelectric.model.CustomUserDetails; import com.capeelectric.model.User;
 * import com.capeelectric.repository.UserRepository; import
 * com.capeelectric.request.AuthenticationRequest; import
 * com.capeelectric.request.ChangePasswordRequest; import
 * com.capeelectric.request.UpdatePasswordRequest; import
 * com.capeelectric.service.impl.AWSEmailService; import
 * com.capeelectric.service.impl.CustomUserDetailsServiceImpl; import
 * com.capeelectric.service.impl.UserDetailsServiceImpl;
 * 
 * @ExtendWith(SpringExtension.class)
 * 
 * @ExtendWith(MockitoExtension.class) public class UserControllerTest {
 * 
 * @InjectMocks private UserController userController;
 * 
 * @MockBean private UserDetailsServiceImpl userDetailsServiceImpl;
 * 
 * @MockBean private CustomUserDetailsServiceImpl userDetailsService;
 * 
 * @MockBean private AuthenticationManager authenticationManager;
 * 
 * @MockBean UsernamePasswordAuthenticationToken
 * usernamePasswordAuthenticationToken;
 * 
 * @MockBean private JwtTokenUtil jwtTokenUtil;
 * 
 * @MockBean private AWSEmailService emailService;
 * 
 * @MockBean private UserRepository userRepository;
 * 
 * private User user;
 * 
 * { user = new User(); user.setUsername("lvsystem@capeindia.net");
 * user.setPassword("moorthy"); user.setEmail("lvsystem@capeindia.net");
 * user.setUserexist(true); user.setActive(true); user.setId(0); }
 * 
 * @Test public void testSaveUser() throws UserException, URISyntaxException,
 * IOException, MessagingException { MockHttpServletRequest request = new
 * MockHttpServletRequest(); RequestContextHolder.setRequestAttributes(new
 * ServletRequestAttributes(request));
 * 
 * when(userDetailsServiceImpl.saveUser(user)).thenReturn(user);
 * 
 * ResponseEntity<Void> addUser = userController.addUser(user);
 * 
 * assertEquals(addUser.getStatusCode(), HttpStatus.CREATED);
 * 
 * }
 * 
 * @Test public void testCreateAuthenticationToken() throws Exception {
 * CustomUserDetails customUserDetails = new CustomUserDetails();
 * customUserDetails.setEmail("lvsystem@capeindia.net");
 * customUserDetails.setPassword("abcd12345");
 * 
 * AuthenticationRequest authenticationRequest = new AuthenticationRequest();
 * authenticationRequest.setEmail("lvsystem@capeindia.net");
 * authenticationRequest.setPassword("abcd12345");
 * 
 * when(userDetailsService.loadUserByUsername("lvsystem@capeindia.net")).
 * thenReturn(customUserDetails);
 * 
 * ResponseEntity<?> token =
 * userController.createAuthenticationToken(authenticationRequest);
 * 
 * assertNotNull(token); }
 * 
 * @Test public void testForgotPassword() throws ForgotPasswordException,
 * IOException, MessagingException, UserException { MockHttpServletRequest
 * request = new MockHttpServletRequest();
 * RequestContextHolder.setRequestAttributes(new
 * ServletRequestAttributes(request));
 * 
 * when(userDetailsServiceImpl.findByUserName("lvsystem@capeindia.net")).
 * thenReturn(user); ResponseEntity<String> forgotPassword =
 * userController.forgotPassword("lvsystem@capeindia.net");
 * assertEquals(forgotPassword.getStatusCode(), HttpStatus.OK); }
 * 
 * @Test public void testUpdatePassword() throws UpdatePasswordException,
 * IOException, MessagingException { UpdatePasswordRequest authenticationRequest
 * = new UpdatePasswordRequest();
 * authenticationRequest.setEmail("lvsystem@capeindia.net");
 * authenticationRequest.setPassword("abcd12345");
 * authenticationRequest.setOtp(1234);
 * 
 * when(userDetailsServiceImpl.updatePassword("lvsystem@capeindia.net",
 * "abcd12345",1234)).thenReturn(user); ResponseEntity<String> updatePassword =
 * userController.updatePassword(authenticationRequest);
 * assertEquals(updatePassword.getBody(), "lvsystem@capeindia.net"); }
 * 
 * @Test public void testChangePassword() throws ChangePasswordException,
 * IOException, MessagingException { ChangePasswordRequest changePasswordRequest
 * = new ChangePasswordRequest();
 * changePasswordRequest.setOldPassword("abcd12345");
 * changePasswordRequest.setEmail("lvsystem@capeindia.net");
 * changePasswordRequest.setPassword("abcd");
 * 
 * when(userDetailsServiceImpl.changePassword("lvsystem@capeindia.net",
 * "abcd12345", "abcd")).thenReturn(user);
 * 
 * ResponseEntity<String> changePassword =
 * userController.changePassword(changePasswordRequest);
 * assertEquals(changePassword.getBody(), "lvsystem@capeindia.net");
 * 
 * }
 * 
 * @Test public void testRetrieveUserInformation() throws UserException {
 * 
 * when(userDetailsServiceImpl.retrieveUserInformation("lvsystem@capeindia.net")
 * ).thenReturn(user); User userInfo =
 * userController.retrieveUserInformation("lvsystem@capeindia.net");
 * assertEquals(userInfo.isUserexist(), true);
 * 
 * }
 * 
 * @Test public void testUpdateUserProfile() throws IOException,
 * MessagingException {
 * when(userDetailsServiceImpl.updateUserProfile(user)).thenReturn(user);
 * ResponseEntity<String> updateUserProfile =
 * userController.updateUserProfile(user);
 * assertEquals(updateUserProfile.getBody(), "lvsystem@capeindia.net"); }
 * 
 * }
 */
