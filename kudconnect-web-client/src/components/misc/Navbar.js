import React from 'react';
import { useKeycloak } from "@react-keycloak/web";
import { NavLink, withRouter } from "react-router-dom/cjs/react-router-dom.min";
import { Container, Dropdown, Menu } from "semantic-ui-react";

const Navbar = (props) => {
    const { keycloak } = useKeycloak();

    const handleLogInOut = () => {
        if (keycloak.authenticated) {
            props.history.push('/');
            keycloak.logout();
        } else {
            keycloak.login();
        }
}

const checkAuthenticated = () => {
    if (!keycloak.authenticated) {
        keycloak.handleLogInOut();
    }
}


const getUsername = () => {
    return keycloak.authenticated && keycloak.tokenParsed && keycloak.tokenParsed.preferred_username
  }

  const getLogInOutText = () => {
    return keycloak.authenticated ? "Logout" : "Login"
  }

  const getAdminMenuStyle = () => {
    return keycloak.authenticated ? { "display": "block" } : { "display": "none" }
  }

  return (
    <Menu stackable>
      <Container>
        <Menu.Item header>Kudconnect</Menu.Item>
        <Menu.Item as={NavLink} exact to="/home">Home</Menu.Item>
        <Menu.Menu position='right'>
          {keycloak.authenticated &&
            <Dropdown text={`Hi ${getUsername()}`} pointing className='link item'>
              <Dropdown.Menu>
                <Dropdown.Item as={NavLink} to="/settings">Settings</Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          }
          <Menu.Item as={NavLink} exact to="/login" onClick={handleLogInOut}>{getLogInOutText()}</Menu.Item>
        </Menu.Menu>
      </Container>
    </Menu >
  )
}
export default withRouter(Navbar);