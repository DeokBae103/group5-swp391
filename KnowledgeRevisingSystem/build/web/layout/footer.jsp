<%-- 
    Document   : footer
    Created on : May 17, 2024, 8:55:16 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

    </head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

    <body>
        <div style="box-shadow: 0px -5px 5px -5px rgba(0, 0, 0, 0.3);">
            <div class="container">
                <footer class="text-center bg-body-tertiary">
                    <!-- Grid container -->
                    <div class="container pt-2">
                        <!-- Section: Social media -->
                        <section class="mb-1">
                            <!-- Facebook -->
                            <a 
                                data-mdb-ripple-init
                                class="btn btn-link btn-floating btn-sm text-body"
                                href="#!"
                                role="button"
                                data-mdb-ripple-color="dark"
                                ><i class="fab fa-facebook-f"></i
                                ></a>


                            <!-- Instagram -->
                            <a
                                data-mdb-ripple-init
                                class="btn btn-link btn-floating btn-sm text-body"
                                href="#!"
                                role="button"
                                data-mdb-ripple-color="dark"
                                ><i class="fab fa-google"></i
                                ></a>


                            <!-- Github -->
                            <a
                                data-mdb-ripple-init
                                class="btn btn-link btn-floating btn-sm text-body"
                                href="#!"
                                role="button"
                                data-mdb-ripple-color="dark"
                                ><i class="fab fa-github"></i
                                ></a>
                        </section>
                        <!-- Section: Social media -->
                    </div>
                    <!-- Grid container -->

                    <!-- Copyright -->
                    <div class="text-center p-1 font-weight-normal" style="font-size:small; background-color: rgba(0, 0, 0, 0.05);">
                        © 2024 Copyright: G5 SWP391

                    </div>
                    <!-- Copyright -->
                </footer>
            </div>
        </div>
        <script>
            import { Ripple, initMDB } from "mdb-ui-kit";

            initMDB({Ripple});
        </script>
    </body>
</html>
