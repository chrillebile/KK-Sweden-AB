import * as React from 'react';
import PalletInterface from 'Models/PalletModel';

interface Props {
  data: PalletInterface[];
}
interface State {
  count: number;
}

export class Table extends React.Component<Props, State> {
  render() {
    const { data } = this.props;
    return (
      <table>
        <thead>
          <tr>
            <th>Id</th>
            <th>Amount</th>
            <th>ProductionDate</th>
            <th>Location</th>
            <th>DeliveryTime</th>
            <th>Blocked</th>
          </tr>
        </thead>
        <tbody>
          {data.map(row => {
            return (
              <tr key={row.id}>
                <td>{row.id}</td>
                <td>{row.amount}</td>
                <td>{row.productionDate}</td>
                <td>{row.location}</td>
                <td>{row.deliveryTime}</td>
                <td>{row.blocked}</td>
              </tr>
            );
          })}
        </tbody>
      </table>
    );
  }
}

export default Table;
