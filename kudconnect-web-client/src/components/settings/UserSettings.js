import { useKeycloak } from '@react-keycloak/web';
// import { handleLogError } from '../misc/Helpers';
// import { getAvatarUrl } from '../misc/Helpers';
import { useHistory } from 'react-router-dom';
import { useState } from 'react';
import { Container, Form, Segment, Button, Divider, Grid } from 'semantic-ui-react';



const UserSettings = () => {

    const [username, setUsername] = useState('');
    const [avatar, setAvatar] = useState('');
    const [originalAvatar, setOriginalAvatar] = useState('');
    const [imageLoading, setImageLoading] = useState(false);
    
    const history = useHistory();
    const { keycloak } = useKeycloak();

    return (
        <Container>
          <Grid centered>
            <Grid.Row>
              <Segment style={{ width: '330px' }}>
                <Form>
                  <strong>Avatar</strong>
                  {/* <div style={{ height: 300 }}>
                    {avatarImage}
                  </div> */}
                  <Divider />
                  {/* <Button fluid onClick={handleSuffle} color='blue' disabled={imageLoading}>Shuffle</Button> */}
                  <Divider />
                  <Button.Group fluid>
                    {/* <Button onClick={handleCancel}>Cancel</Button> */}
                    <Button.Or />
                    {/* <Button disabled={originalAvatar === avatar} onClick={handleSave} positive>Save</Button> */}
                  </Button.Group>
                </Form>
              </Segment>
            </Grid.Row>
          </Grid>
        </Container>
      );

}

export default UserSettings;