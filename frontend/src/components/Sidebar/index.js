import React, { Component } from 'react';
import { Divider, Form, Select, Typography } from 'antd';

const { Title } = Typography;
const { Option } = Select;

class Index extends Component {
  render() {
    return (
      <div style={{ margin: 15 }}>
        <Title level={3} style={{ marginTop: 16 }}>JagerMilkyWay</Title>
        <Divider />
        <Form layout="vertical">
          <Form.Item label="Type" name="requiredMark">
            <Select defaultValue="lucy" style={{ width: '100%' }} label="Type">
              <Option value="jack">Jack</Option>
              <Option value="Yiminghe">yiminghe</Option>
            </Select>
          </Form.Item>
        </Form>
      </div>
    );
  }
}

export default Index;