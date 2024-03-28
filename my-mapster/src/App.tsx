import './App.css'
import PostListPage from "./post/list/PostListPage.tsx";
import {Route, Routes} from "react-router-dom";
import DefaultLayout from "./containers/default/DefaultLayout.tsx";
import PostCreatePage from "./post/create/PostCreatePage.tsx";
import PostEditPage from "./post/edit/CategoryEditPage.tsx";
function App() {

    return (
        <>
            <Routes>
                <Route path={"/"} element={<DefaultLayout/>}>
                    <Route index element={<PostListPage/>}/>
                    <Route path={"post"}>
                        <Route path = "create" element={<PostCreatePage/>}/>
                    </Route>
                    <Route path={"edit/:id"} element={<PostEditPage/>} />
                </Route>
            </Routes>
        </>
    )
}

export default App