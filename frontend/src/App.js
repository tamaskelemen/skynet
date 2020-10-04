import React, { Component } from 'react';
import './App.css';
import ContractMap from './views/ContractMap';

class App extends Component {
  state = {
    activePage: '0',
  };

  handlePageChange = activePage => this.setState({ activePage });

  showPage(activePage, handlePageChange) {
    switch (activePage) {
      case 0:
        return <ContractMap activePage={activePage} handlePageChange={handlePageChange} />;
      case 1:
        return <ContractMap activePage={activePage} handlePageChange={handlePageChange} />;
      case 2:
        return <ContractMap activePage={activePage} handlePageChange={handlePageChange} />
      default:
        return <ContractMap activePage={activePage} handlePageChange={handlePageChange} />;
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
