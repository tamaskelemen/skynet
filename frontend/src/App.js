import React, { Component } from 'react';
import './App.css';
import MainMap from './views/MainMap';

class App extends Component {
  state = {
    activePage: '0',
  };

  handlePageChange = activePage => this.setState({ activePage });

  showPage(activePage, handlePageChange) {
    switch (activePage) {
      case 0:
        return <MainMap activePage={activePage} handlePageChange={handlePageChange} />;
      case 1:
        return <MainMap activePage={activePage} handlePageChange={handlePageChange} />;
      default:
        return <MainMap activePage={activePage} handlePageChange={handlePageChange} />;
    }
  }

  render() {
    const { activePage } = this.state;

    return (
      <div className="App">
        {this.showPage(activePage, this.handlePageChange)}
      </div>
    );
  }
}

export default App;
