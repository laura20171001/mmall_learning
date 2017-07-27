//service interface implement


package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse login(String username,String password){
        int resultCount=userMapper.checkUsername(username);
        if(resultCount == 0){

            return ServerResponse.createByErrorMessage("The username doesn't exist!");
        }
        //todo passwordloginMD5
        String md5Password = MD5Util.MD5EncodingUtf8(password);
        User user=userMapper.selectLogin(username,md5Password);
        if(user == null){

            return ServerResponse.createByErrorMessage("The password error !");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("Login success",user);
    }

    public ServerResponse<String > register(User user){
        int resultCount=userMapper.checkUsername(user.getUsername());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("The username already exists!");
        }

        ServerResponse validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
            if(validResponse.isSuccess()) {
                return  validResponse;
            }
        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if(validResponse.isSuccess()) {
            return  validResponse;
        }

        resultCount = userMapper.checkEmail(user.getEmail());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("The email already exists!");
        }

        user.setRole(Const.Role.ROLL_CUSTOMER);
        //todo MD5 encrypt
        user.setPassword(MD5Util.MD5EncodingUtf8(user.getPassword()));
        resultCount=userMapper.insert(user);
        if(resultCount == 0){

            return ServerResponse.createByErrorMessage("The register failed");
        }
        return ServerResponse.createBySuccessMessage("Register success");
    }

    public ServerResponse<String> checkValid(String str, String type) {
        if(org.apache.commons.lang3.StringUtils.isNoneBlank(type)){
            if(Const.USERNAME.equals(type)){
                int resultCount=userMapper.checkUsername(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("The username already exists!");
                }
            }
            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(type);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("The email already exists!");
                }
                }
        }else {
            return ServerResponse.createByErrorMessage("The parameter wrong");
        }
      return ServerResponse.createBySuccessMessage("Validation success");
    }

    public ServerResponse<String> selectionQuestion(String username){
        ServerResponse validResponse = this.checkValid(username,Const.CURRENT_USER);
        if(validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("The user doesn't exist.");
        }
        String question = userMapper.selectionQuestionByUsername(username);
        if(org.apache.commons.lang3.StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("Find password 's question is empty");
    }

    public ServerResponse<String> checkAnswer(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount>0){
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("The answer is wrong");
    }

    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        if(org.apache.commons.lang3.StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("The paramassage is wrong,'token' is requested.");
        }
        ServerResponse validResponse = this.checkValid(username,Const.CURRENT_USER);
        if(validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("The user doesn't exist.");
        }

        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);

        if(org.apache.commons.lang3.StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("The token is invalid or expired.");
        }


        if(org.apache.commons.lang3.StringUtils.equals(forgetToken,token)){
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount =userMapper.updatePasswordByUsername(username,md5Password);
            if(rowCount > 0){
                return ServerResponse.createBySuccessMessage("Update password success.");
            }
        }else {
            return ServerResponse.createByErrorMessage("Token is wrong, please get a new one");
        }

        return ServerResponse.createByErrorMessage("Failed to update password.");

    }

    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user){
        //prevent hengxiang yuequan , need to validate the username's old password

        int resultCount=userMapper.checkPassword(MD5Util.MD5EncodingUtf8(passwordOld),user.getId());
        if(resultCount == 0){

            return ServerResponse.createByErrorMessage("The old passowrd is not correct.");
        }

        user.setPassword(MD5Util.MD5EncodingUtf8(passwordNew)) ;
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 0 ) {
            return ServerResponse.createBySuccessMessage("Update password success.");
        }
        return ServerResponse.createByErrorMessage("Failed to update password.");
    }

    public ServerResponse<User> updateInformation(User user){
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());

        if(resultCount > 0){

            return ServerResponse.createByErrorMessage("The email exists. please use a new email.");
        }

        User updateUser=new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount=userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0){

            return ServerResponse.createBySuccess("Update Personal Information Success.",updateUser);
        }
        return ServerResponse.createByErrorMessage("Update Personal Information failed.");
    }



    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null){
            return ServerResponse.createByErrorMessage("Can not find the user.");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

  //backend
    //Validate whether the user is admin or not;
    public ServerResponse checkAdminRoll(User user){
        if(user != null && user.getRole().intValue() == Const.Role.ROLL_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }


}