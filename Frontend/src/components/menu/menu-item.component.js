// Each menu item in the drawer
import React, { Component } from 'react';
import { NavItem, NavIcon, NavText } from '@trendmicro/react-sidenav';

import '@trendmicro/react-sidenav/dist/react-sidenav.css';

class MenuItem extends Component {
    render() {
        const { title } = this.props;

        return(
            <NavItem eventKey="home">
                <NavIcon>
                    <i className="fa fa-fw fa-home" style={{ fontSize: '1.75em' }} />
                </NavIcon>
                <NavText>
                    {title}
                </NavText>
            </NavItem>
        );
    }
}

export default MenuItem;