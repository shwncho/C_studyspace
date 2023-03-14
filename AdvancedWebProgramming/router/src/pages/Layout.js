import {Outlet, Link} from "react-router-dom";

const Layout=()=>{
    return(
        <>
        <nav>
            <ul>
                <li>
                    <Link to ="/">Home</Link>
                </li>
                <li>
                    <Link to="/carinfo">CarInfo</Link>
                </li>
                <li>
                    <Link to="/userinfo">UserInfo</Link>
                </li>
            </ul>
        </nav>
        <Outlet/>
        </>
    );
};

export default Layout;