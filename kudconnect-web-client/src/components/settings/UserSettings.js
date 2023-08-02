import { useKeycloak } from '@react-keycloak/web';
import { handleLogError } from '../misc/Helpers';
import { getAvatarUrl } from '../misc/Helpers';
import { kudconnectApi } from '../misc/KudconnectApi';
import { useHistory } from 'react-router-dom';
import { useState } from 'react';
import { useEffect } from 'react';
import { Container, Form, Segment, Button, Divider, Grid } from 'semantic-ui-react';



const UserSettings = () => {

    const [username, setUsername] = useState('');
    const [avatar, setAvatar] = useState('');
    const [originalAvatar, setOriginalAvatar] = useState('');
    const [imageLoading, setImageLoading] = useState(false);

    const history = useHistory();
    const { keycloak } = useKeycloak();

    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await kudconnectApi.getUserDetails(keycloak.token);
            const { username, avatar } = response.data;
            setUsername(username);
            setAvatar(avatar);
            setOriginalAvatar(avatar);
          } catch (error) {
            handleLogError(error);
          }
        }
        fetchData();
      }, [keycloak.token]);

    const handleSuffle = () => {
        setImageLoading(true);
        const avatar = username + Math.floor(Math.random() * 1000) + 1;
        setAvatar(avatar);
    };

    const handleImageLoad = () => {
        setImageLoading(false);
    };

    const handleCancel = () => {
        history.push("/home");
    };

    const handleSave = async () => {
        try {
            const userDetails = { avatar };
            await kudconnectApi.saveUserDetails(keycloak.token, userDetails);
            keycloak['avatar'] = avatar;
            history.push("/home");
        } catch (error) {
            handleLogError(error);
        }
    };


    const avatarImage = !avatar ? <></> : <img src={getAvatarUrl(avatar)} onLoad={handleImageLoad} alt='user-avatar' />

    return (
        <Container>
        <Grid centered>
          <Grid.Row>
            <Segment style={{ width: '330px' }}>
              <Form>
                <strong>Avatar</strong>
                <div style={{ height: 300 }}>
                  {avatarImage}
                </div>
                <Divider />
                <Button fluid onClick={handleSuffle} color='blue' disabled={imageLoading}>Shuffle</Button>
                <Divider />
                <Button.Group fluid>
                  <Button onClick={handleCancel}>Cancel</Button>
                  <Button.Or />
                  <Button disabled={originalAvatar === avatar} onClick={handleSave} positive>Save</Button>
                </Button.Group>
              </Form>
            </Segment>
          </Grid.Row>
        </Grid>
      </Container>
    );

}

export default UserSettings;