import React, { Component } from 'react';
import { API_URL } from '../common/constants';
import Map from '../components/Map';
import Menus from '../components/Menus';

export default class MemberMap extends Component {
  constructor(props) {
    super(props);

    this.state = {
      members: [],
    };
  }

  getData = async (url = '', data = {}) => {
    const queryParam = this.parseQueryParams(data);
    const response = await fetch(url + queryParam, {
      headers: {
        'Content-Type': 'application/json',
      },
    });

    return await response.json();
  };

  parseQueryParams = (data = {}) => {
    let queryParam = '?';
    for (const property in data) {
      queryParam += property;
      queryParam += '=';
      queryParam += data[property];
      queryParam += '&';
    }

    return queryParam;
  };

  componentDidMount() {
    this.getData(API_URL + '/members/all')
      .then(response => this.setState({ members: response }));
  }

  render() {
    const { members } = this.state;
    const { activePage, handlePageChange } = this.props;
    return (
      <Menus activePage={activePage} handlePageChange={handlePageChange}>
        <div style={{ display: 'flex', flexDirection: 'column' }}>
        <span className="haliho">Here are the institutions where the proffesional IAU members work.</span>
          <Map members={members} />
        </div>
      </Menus>
    );
  }
}