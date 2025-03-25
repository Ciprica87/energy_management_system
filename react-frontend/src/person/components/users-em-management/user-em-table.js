import React from "react";
import Table from "../../../commons/tables/table";

const columns = [
    {
        Header: 'Energy Meter ID',
        accessor: 'energyMeterId',
    },
    {
        Header: 'User ID',
        accessor: 'userID',
    }
];

const filters = [
];

class EnergyMeterTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tableData: this.props.tableData
        };
    }

    render() {
        return (
            <Table
                data={this.state.tableData}
                columns={columns}
                search={filters}
                pageSize={5}
            />
        )
    }
}

export default EnergyMeterTable;