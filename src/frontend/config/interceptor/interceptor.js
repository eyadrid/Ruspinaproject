import axios from "axios";
import {notification } from "antd";
import {BaseUrl} from '../constant/URLS';
import {RefreshToken} from '../../service/login/AuthService';

const unauthroizedCode = [403];

const Interceptor = axios.create({
    baseURL : BaseUrl,
    timeout : 60000
});

const TOKEN_PAYLOAD_KEY = "Authorization";


Interceptor.interceptors.request.use(
    (config) => {
        const AUTH_TOKEN = localStorage.getItem("token");
        const isRefreshTokenRequest = 
            config.url.includes("refresh") ||
            config.url.includes("login");
        if (!isRefreshTokenRequest) {
            const jwtToken = AUTH_TOKEN || null;
            if (jwtToken) {

                config.headers[TOKEN_PAYLOAD_KEY] = `Bearer ${jwtToken}`;
            }
        }
        if (isRefreshTokenRequest) {
             config.headers[TOKEN_PAYLOAD_KEY] = null;
        }

        return config
    },
    (error) => {
        notification.error({
            message : "Error",
        });
        Promise.reject(error);
    }
)

Interceptor.interceptors.response.use(
    (response) => {
        return response.data;
    },
    (error) => {
        
        let notificationParam = {
            message  : "",
        };
        if(unauthroizedCode.includes(error.response.status))
        {

            notificationParam.message = "You are not authorized to access this resource";
            notificationParam.description = "Token expired";
            const accessToken = localStorage.getItem("token");
            const refreshToken = localStorage.getItem("refreshToken");
            RefreshToken(refreshToken,accessToken).then((res)=>{
                localStorage.setItem("token",res.data.accessToken);
                localStorage.setItem("refreshToken",res.data.refreshToken);
                window.location.reload();
            })
            .catch((err)=>{
               console.log(err);
            });
            
        }
        if(error.response.status === 401)
        {
            notificationParam.message = "Unauthorized";

            notificationParam.description = error.response.data.errors[0];
        }
        if(error.response.status === 500)
        {
            notificationParam.message = "Internal Server Error";
            notificationParam.description = "Please try again later";
        }
        if(error.response.status === 404)
        {
            notificationParam.message = "Not Found";
            notificationParam.description = "Please try again later";
        }
        if(error.response.status === 400)
        {
            notificationParam.message = "Bad Request";
            notificationParam.description = error.response.data.errors[0];
        }
        notification.error(notificationParam);
        
        return Promise.reject(error);

    }
);

export default Interceptor;