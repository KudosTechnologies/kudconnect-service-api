import React from 'react';
import './App.css';
import { ReactKeycloakProvider } from '@react-keycloak/web';
import Keycloak from 'keycloak-js';
import { Dimmer, Header, Icon } from 'semantic-ui-react'
import { config } from './Constants';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Navbar from './components/misc/Navbar';
import PrivateRoute from './components/misc/PrivateRoute';
import Home from './components/home/Home';
import UserSettings from './components/settings/UserSettings';
import { useState } from 'react';


function App() {
  const keycloak = new Keycloak(
    {
      url: config.url.KEYCLOAK_BASE_URL,
      realm: 'kudconnect',
      clientId: 'kudconnect-webapp'
    }
  )

  const initOptions = { pkceMethod: 'S256' }

  const handleOnEvent = (event, error) => {
    if (event === 'onAuthSuccess') {
      console.log('onAuthSuccess')
    }
  }

  const loadingComponent = (
    <Dimmer inverted active={true} page>
      <Header style={{ color: '#4d4d4d' }} as='h2' icon inverted>
        <Icon loading name='cog' />
        <Header.Content>Keycloak is loading
          <Header.Subheader style={{ color: '#4d4d4d' }}>or running authorization code flow with PKCE</Header.Subheader>
        </Header.Content>
      </Header>
    </Dimmer>
  )
    


  return (
    <ReactKeycloakProvider
      authClient={keycloak}
      initOptions={initOptions}
      LoadingComponent={loadingComponent}
      onEvent={(event, error) => handleOnEvent(event, error)}
    >
      <Router>
        <Navbar />
        <Switch>
          <Route path='/' exact component={Home} />
          <Route path='/home' component={Home} />
          <PrivateRoute path='/settings' component={UserSettings} />
        </Switch>
      </Router>
    </ReactKeycloakProvider>
  );
}

export default App;
