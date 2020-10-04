import React, { Component } from 'react';
import { API_URL } from '../common/constants';
import Map from '../components/Map';
import Menus from '../components/Menus';

export default class UsaAstroClubs extends Component {
  constructor(props) {
    super(props);

    this.state = {
      clubs: [],
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
    this.getData(API_URL + '/clubs/all')
      .then(response => this.setState({ clubs: response }));
  }

  render() {
    const { clubs } = this.state;
    const { activePage, handlePageChange } = this.props;
    return (
      <Menus activePage={activePage} handlePageChange={handlePageChange}>
        <div style={{ display: 'flex', flexDirection: 'column' }}>
        <span className="haliho">Here are the amateur astronomical clubs all around the US.</span>
          <Map clubs={clubs} />
        </div>
      </Menus>
    );
  }
}