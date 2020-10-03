import React, { Component } from 'react';
import Map from '../components/Map';
import Menus from '../components/Menus';

class MainMap extends Component {
  render() {
    const { activePage, handlePageChange } = this.props;
    return (
      <Menus activePage={activePage} handlePageChange={handlePageChange}>
        <Map />
      </Menus>
    );
  }
}

export default MainMap;