// import React, { useState } from "react";
// import axios from 'axios';
// import { useNavigate } from 'react-router-dom';

// const Login = () => {
//   const [isLogin, setIsLogin] = useState(true);
//   const [userName, setUserName] = useState("");
//   const [email, setEmail] = useState("");
//   const [phoneNumber, setPhoneNumber] = useState("");
//   const [address, setAddress] = useState("");
//   const [password, setPassword] = useState("");
//   const navigate = useNavigate();

//   const toggleMode = () => {
//     setIsLogin(!isLogin);
//   };

//   const handleSubmit = async (event) => {
//     event.preventDefault();
//    if(isLogin)
//     {
//       const response = await axios.post("http://localhost:8080/auth/login",{
//         userName,
//         password
//       })
//       const token = response.data;
//       console.log("Token:", token);
//       localStorage.setItem("token", token);
//     }else{
//       const response = await axios.post("http://localhost:8080/auth/addUser",{
//         userName,
//         email,
//         phoneNumber,
//         address,
//         password
//       })
//       const token = response.data;
//       console.log("Token:", token);
//       localStorage.setItem("token", token);
//     }

//     navigate("/");
//   };

//   return (
//     <div className="limiter">
//       <div className="container-login100">
//         <div className="wrap-login100">
//           <form onSubmit={handleSubmit} className="login100-form validate-form">
//             <span className="login100-form-title p-b-26">
//               {isLogin ? "Welcome" : "Sign Up"}
//             </span>
//             <span className="login100-form-title p-b-48">
//               <i className="zmdi zmdi-font"></i>
//             </span>

//             <div className="wrap-input100 validate-input" data-validate="Valid username is required">
//               <input 
//                 className="input100" 
//                 type="text" 
//                 name="username" 
//                 value={userName} 
//                 onChange={(e) => setUserName(e.target.value)} 
//                 required 
//               />
//               <span className="focus-input100" data-placeholder="Username"></span>
//             </div>

//             {!isLogin && (
//               <>
//                 <div className="wrap-input100 validate-input" data-validate="Valid email is: a@b.c">
//                   <input 
//                     className="input100" 
//                     type="email" 
//                     name="email" 
//                     value={email} 
//                     onChange={(e) => setEmail(e.target.value)} 
//                     required 
//                   />
//                   <span className="focus-input100" data-placeholder="Email"></span>
//                 </div>

//                 <div className="wrap-input100 validate-input" data-validate="Enter phone number">
//                   <input 
//                     className="input100" 
//                     type="tel" 
//                     name="phoneNumber" 
//                     value={phoneNumber} 
//                     onChange={(e) => setPhoneNumber(e.target.value)} 
//                     required 
//                   />
//                   <span className="focus-input100" data-placeholder="Phone Number"></span>
//                 </div>

//                 <div className="wrap-input100 validate-input" data-validate="Enter address">
//                   <input 
//                     className="input100" 
//                     type="text" 
//                     name="address" 
//                     value={address} 
//                     onChange={(e) => setAddress(e.target.value)} 
//                     required 
//                   />
//                   <span className="focus-input100" data-placeholder="Address"></span>
//                 </div>
//               </>
//             )}

//             <div className="wrap-input100 validate-input" data-validate="Enter password">
//               <span className="btn-show-pass">
//                 <i className="zmdi zmdi-eye"></i>
//               </span>
//               <input 
//                 className="input100" 
//                 type="password" 
//                 name="pass" 
//                 value={password} 
//                 onChange={(e) => setPassword(e.target.value)} 
//                 required 
//               />
//               <span className="focus-input100" data-placeholder="Password"></span>
//             </div>

//             <div className="container-login100-form-btn">
//               <div className="wrap-login100-form-btn">
//                 <div className="login100-form-bgbtn"></div>
//                 <button className="login100-form-btn">
//                   {isLogin ? "Login" : "Sign Up"}
//                 </button>
//               </div>
//             </div>

//             <div className="text-center p-t-115 bg-slate-950">
//               <span className="txt1">
//                 {isLogin ? "Don't have an account?" : "Already have an account?"}
//               </span>

//               <button type="button" className="txt2" onClick={toggleMode}>
//                 {isLogin ? "Sign Up" : "Login"}
//               </button>
//             </div>
//           </form>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default Login;

import React, { useState } from "react";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [address, setAddress] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const toggleMode = () => {
    setIsLogin(!isLogin);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (isLogin) {
      const response = await axios.post("http://localhost:8080/auth/login", {
        userName,
        password
      });
      const token = response.data;
      console.log("Token:", token);
      localStorage.setItem("token", token);
    } else {
      const response = await axios.post("http://localhost:8080/auth/addUser", {
        userName,
        email,
        phoneNumber,
        address,
        password
      });
      const token = response.data;
      console.log("Token:", token);
      localStorage.setItem("token", token);
    }

    navigate("/");
  };

  return (
    <div className="limiter">
      <div className="container-login100">
        <div className="wrap-login100">
          <form onSubmit={handleSubmit} className="login100-form validate-form">
            <span className="login100-form-title p-b-26">
              {isLogin ? "Welcome" : "Sign Up"}
            </span>
            {/* <span className="login100-form-title p-b-48">
              <i className="zmdi zmdi-font"></i>
            </span> */}

            <div className="wrap-input100 validate-input" data-validate="Valid username is required">
              <input 
                className="input100" 
                type="text" 
                name="username" 
                value={userName} 
                onChange={(e) => setUserName(e.target.value)} 
                required 
              />
              <span className="focus-input100" data-placeholder="Username"></span>
            </div>

            {!isLogin && (
              <>
                <div className="wrap-input100 validate-input" data-validate="Valid email is: a@b.c">
                  <input 
                    className="input100" 
                    type="email" 
                    name="email" 
                    value={email} 
                    onChange={(e) => setEmail(e.target.value)} 
                    required 
                  />
                  <span className="focus-input100" data-placeholder="Email"></span>
                </div>

                <div className="wrap-input100 validate-input" data-validate="Enter phone number">
                  <input 
                    className="input100" 
                    type="tel" 
                    name="phoneNumber" 
                    value={phoneNumber} 
                    onChange={(e) => setPhoneNumber(e.target.value)} 
                    required 
                  />
                  <span className="focus-input100" data-placeholder="Phone Number"></span>
                </div>

                <div className="wrap-input100 validate-input" data-validate="Enter address">
                  <input 
                    className="input100" 
                    type="text" 
                    name="address" 
                    value={address} 
                    onChange={(e) => setAddress(e.target.value)} 
                    required 
                  />
                  <span className="focus-input100" data-placeholder="Address"></span>
                </div>
              </>
            )}

            <div className="wrap-input100 validate-input" data-validate="Enter password">
              <span className="btn-show-pass">
                <i className="zmdi zmdi-eye"></i>
              </span>
              <input 
                className="input100" 
                type="password" 
                name="pass" 
                value={password} 
                onChange={(e) => setPassword(e.target.value)} 
                required 
              />
              <span className="focus-input100" data-placeholder="Password"></span>
            </div>

            <div className="container-login100-form-btn">
              <div className="wrap-login100-form-btn">
                <div className="login100-form-bgbtn"></div>
                <button className="login100-form-btn">
                  {isLogin ? "Login" : "Sign Up"}
                </button>
              </div>
            </div>

            <div className="text-center p-t-115 bg-slate-950">
              <span className="txt1">
                {isLogin ? "Don't have an account?" : "Already have an account?"}
              </span>

              <button type="button" className="txt2" onClick={toggleMode}>
                {isLogin ? "Sign Up" : "Login"}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Login;