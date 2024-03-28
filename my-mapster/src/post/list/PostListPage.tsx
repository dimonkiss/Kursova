import {Button, Popconfirm, Table} from "antd";
import {Link} from "react-router-dom";
import {ColumnsType} from "antd/es/table";
import {IPostItem} from "../types.ts";
import {EditOutlined, DeleteOutlined} from '@ant-design/icons';
import {useEffect, useState} from "react";
import {APP_ENV} from "../../env";
import http_common from "../../http_common.ts";

const PostListPage = () => {
    const imgURL = APP_ENV.BASE_URL + "/uploading/150_";

    const columns: ColumnsType<IPostItem> = [
        {
            title: 'Id',
            dataIndex: 'id',
        },
        {
            title: 'Name',
            dataIndex: 'name',
        },
        {
            title: 'Description',
            dataIndex: 'description',
        },
        {
            title: 'Image',
            dataIndex: 'image',
            render: (imageName: string) => (
                <img src={`${imgURL}${imageName}`} alt="post Image"/>
            ),
        },
        {
            title: 'Edit',
            dataIndex: 'edit',
            render: (_, record) => (
                <Link to={`/post/edit/${record.id}`}>
                    <Button type="primary" icon={<EditOutlined/>}>
                        Змінить
                    </Button>
                </Link>

            ),
        },
        {
            title: 'Delete',
            dataIndex: 'delete',
            render: (_, record) => (

                <Popconfirm
                    title="Are you sure to delete this post?"
                    onConfirm={() => handleDelete(record.id)}
                    okText="Yes"
                    cancelText="No"
                >
                    <Button icon={<DeleteOutlined/>}>
                        Delete
                    </Button>
                </Popconfirm>

            ),
        },
    ];

    const [data, setData] = useState<IPostItem[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await http_common.get("http://localhost:8080/api/posts");
                console.log("response.data", response.data)
                setData(response.data);
            } catch (error) {
                console.error('Error fetching categories:', error);
            }
        };

        fetchData();
    }, []);

    const handleDelete = async (postId: number) => {
        try {
            await http_common.delete(`http://localhost:8080/api/posts/${postId}`);
            setData(data.filter(x => x.id != postId));
        } catch (error) {
            throw new Error(`Error: ${error}`);
        }
    };

    return (
        <>
            <h1>Список категорій</h1>
            <Link to={"/post/create"}>
                <Button type="primary" style={{margin: '5px'}}>
                    ADD +
                </Button>
            </Link>

            <Table columns={columns} rowKey={"id"} dataSource={data} size="middle"/>
        </>
    );
}

export default PostListPage;