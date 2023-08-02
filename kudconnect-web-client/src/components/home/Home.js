import { useKeycloak } from "@react-keycloak/web";
import { getUsername } from '../misc/Helpers';
import { Container, Header } from 'semantic-ui-react';

const Home = () => {
    const { keycloak } = useKeycloak();

    return (
        <Container>
            <Header>Welcome Home {getUsername(keycloak)} </Header>
        </Container>
    )
}

export default Home;