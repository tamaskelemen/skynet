import React, { Component } from 'react';
import { Button, DatePicker, Form, Input, Layout, Menu, Space } from 'antd';
import { ContainerOutlined, DesktopOutlined, MenuOutlined, PieChartOutlined, PlayCircleOutlined, SolutionOutlined } from '@ant-design/icons';
import Animation from './Animation';

const { Sider, Header } = Layout;
const { RangePicker } = DatePicker;
const { SubMenu } = Menu;

class Index extends Component {
  state = {
    collapsed: true,
  };

  handleCollapseClick = () => {
    const { collapsed } = this.state;
    this.setState({ collapsed: !collapsed });
  };

  render() {
    const { 
      children,
      activePage,
      handlePageChange,
      searchValueChanged,
      searchValue,
      observationsChange,
      animation,
      setAnimation,
      meteorChanged } = this.props;
    const { collapsed } = this.state;
    const layout = {
      labelCol: { span: 8 },
      wrapperCol: { span: 16 },
    };

    return (
      <Layout>
        <Header className="header">
          <Button ghost className="menu-button" onClick={this.handleCollapseClick}><MenuOutlined /></Button>
          <div className="logo"><img src="./logo.png" alt="" />JägerMilkyWay</div>
          <Menu theme="dark" mode="horizontal" defaultSelectedKeys={[activePage]}>
            <Menu.Item key="0" onClick={() => handlePageChange('0')}><SolutionOutlined />Space contracts</Menu.Item>
            <Menu.Item key="1" onClick={() => handlePageChange('1')}><PieChartOutlined />Space projects</Menu.Item>
            <Menu.Item key="2" onClick={() => handlePageChange('2')}>Observations</Menu.Item>
            <Menu.Item key="3" onClick={() => handlePageChange('3')}>Meteors</Menu.Item>
            <Menu.Item key="4" onClick={() => handlePageChange('4')}>IAU Members</Menu.Item>
            <Menu.Item key="5" onClick={() => handlePageChange('5')}>US Astronomical Clubs</Menu.Item>
          </Menu>
        </Header>

        <Layout>
          <Sider width={collapsed ? 0 : 300} theme="light">
            <Menu
              defaultSelectedKeys={['1']}
              defaultOpenKeys={['sub1']}
              mode="inline"
              theme="dark"
            >
              <SubMenu key="sub1" icon={<PlayCircleOutlined />} title="Animation">
                <Menu.Item key="anim" style={{ height: 'auto', backgroundColor: 'white', paddingLeft: '16px !important' }}>
                  <Animation animation={animation} setAnimation={setAnimation} />
                </Menu.Item>
              </SubMenu>
              <Menu.Item key="2" icon={<DesktopOutlined />}>
                Option 2
              </Menu.Item>
              <Menu.Item key="3" icon={<ContainerOutlined />}>
                Option 3
              </Menu.Item>

              <Form
                {...layout}
                name="form"
                onFinish={searchValueChanged}>
                <Form.Item label="Project name" name="project_name">
                  <Input value={searchValue} />
                </Form.Item>
                <Form.Item>
                  <Button type="primary" htmlType="submit" onClick={searchValueChanged}>
                    Submit
                  </Button>
                </Form.Item>
              </Form>
              <Space direction="vertical" size={12}>
                <RangePicker
                  showTime={{ format: 'HH:mm' }}
                  format="YYYY-MM-DD HH:mm"
                  onChange={observationsChange}
                />
              </Space>,

              <Space direction="vertical" size={12}>
                <RangePicker
                  showTime={{ format: 'HH:mm' }}
                  format="YYYY-MM-DD HH:mm"
                  onChange={meteorChanged}
                />
              </Space>,
            </Menu>
          </Sider>

          {children}

        </Layout>
      </Layout>
    );
  }
}

export default Index;