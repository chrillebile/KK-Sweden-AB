import * as React from 'react';
import PalletInterface from 'Models/PalletModel';

interface Props {
  row: PalletInterface;
}
interface State {
}

export class TableRow extends React.Component<Props, State> {
  render() {
    const { row } = this.props;
    return (
      <tr>
        <td key={row.id}>{row.id}</td>
        <td key={row.amount}>{row.amount}</td>
      </tr>
    );
  }
}

export default TableRow;
