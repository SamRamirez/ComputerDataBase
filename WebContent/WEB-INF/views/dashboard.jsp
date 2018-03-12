<%@ page pageEncoding = "UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="ServletDashboard"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${nbCompu} Computers found 
                <br> page number ${page}
<%--                 <c:if test = "(${filter == null || filter == \"\" })"> --%>
                	<br> ${filter == "" || filter == null} true
<%--                 </c:if> --%>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="ServletDashboard" method="GET" class="form-inline">
						<c:if test = "${filter != null && filter != \"\"}">
						    <input type="search" id="searchbox" name="filter" class="form-control" placeholder="${filter}" />
							<input type="submit" id="searchsubmit" value="Filter Reset" class="btn btn-primary" />
						</c:if>
						<c:if test = "${filter == null || filter == \"\" }">
                        	<input type="search" id="searchbox" name="filter" class="form-control" placeholder="Search name" />
                       		<input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
                        </c:if>       
                    </form>
                </div>
                <div class="pull-right">

                	<a class="btn btn-success" id="addComputer" href="ServletAddComputer">Add Computer</a>
                
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
<!--                         Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
<!--                         Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                
                <!-- diff ici -->
                <tbody id="results">

					<c:forEach items="${listComputers}" var="entry">
						<tr>

							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${entry.id }"></td>
							<!-- 							<tr> -->
							<td><a href="ServletEdit?id=${entry.id }" onclick="">${entry.name}</a>
							</td>
							<td>${entry.introduced}</td>
							<td>${entry.discontinued}</td>
							<td>${entry.companyName}</td>
						</tr>
					</c:forEach>


				</tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
            
            
<!--                 <li> -->
<!--                     <a href="#" aria-label="Previous"> -->
<!--                       <span aria-hidden="true">&laquo;</span> -->
<!--                   </a> -->
<!--               </li> -->


				<c:if test = "${localisationPages > 5}">
					<li><a
						href="ServletDashboard?localisationNext=${localisationNext - 5 }&page=${page}&localisationPages=${localisationPages}"> 5 previous pages </a>
					</li>
				</c:if>
				
<%-- 				<c:if test = "${localisationPages > 1}"> --%>
<!-- 					<li><a -->
<%-- 						href="ServletDashboard?localisationNext=${localisationNext}&page=${page-1}&localisationPages=${localisationPages}" aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a> --%>
<!-- 					</li> -->
<%-- 				</c:if> --%>

				<c:if test="${localisationPages > 1}">
					<li><a
						href="ServletDashboard?localisationNext=${localisationNext}&page=${page-1}&localisationPages=${localisationPages}&filter=${filter}"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a>
					</li>
				</c:if>


				<c:if test="${ (localisationPages + 4) <= (maxPage) }">
					<c:forEach var="i" begin="${localisationPages}" end="${ localisationPages + 4}">
						<li><a
							href="ServletDashboard?page=${i}&localisationPages=${localisationPages}&localisationNext=${localisationNext}&filter=${filter}">${i}</a></li>
					</c:forEach>
				</c:if>
				<!--else -->
				<c:if test="${ (localisationPages + 4) > maxPage}">
				<c:forEach var="i" begin="${localisationPages}" end="${maxPage}">
						<li><a
							href="ServletDashboard?page=${i}&localisationPages=${localisationPages}&localisationNext=${localisationNext}&filter=${filter}">${i}</a></li>
					</c:forEach>
				</c:if>
				
				<c:if test = "${ localisationPages < maxPage}">
					<li><a
						href="ServletDashboard?localisationNext=${localisationNext}&page=${page + 1}&localisationPages=${localisationPages}&filter=${filter}" aria-label="Next"> 
                     <span aria-hidden="true">&raquo;</span></a></li>
				</c:if>


				<c:if test = "${(localisationPages + 4) <= maxPage}">
					<li><a href="ServletDashboard?localisationNext=${localisationNext + 5 }&page=${page}&localisationPages=${localisationPages}&filter=${filter}"> 5 next pages </a></li>
				</c:if>

				<!--               <li> -->
<!--                 <a href="#" aria-label="Next"> -->
<!--                     <span aria-hidden="true">&raquo;</span> -->
<!--                 </a> -->
<!--             </li> -->
            
        </ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
			
			<input type="button" class="btn btn-default"
						onclick="location.href='ServletDashboard?page=1&localisationNext=0'"
						value="1" />

				<c:if test="${ (maxPage)>=10 }">
					<input type="button" class="btn btn-default"
						onclick="location.href='ServletDashboard?page=10&localisationNext=10'"
						value="10" />
				</c:if>
				<c:if test="${ (maxPage)>=50 }">
					<input type="button" class="btn btn-default"
						onclick="location.href='ServletDashboard?page=50&localisationNext=50'"
						value="50" />
				</c:if>
				<c:if test="${ (maxPage)>=100 }">
					<input type="button" class="btn btn-default"
						onclick="location.href='ServletDashboard?page=100&localisationNext=100'"
						value="100" />
				</c:if>
				
				<input type="button" class="btn btn-default"
						onclick="location.href='ServletDashboard?page=${maxPage}&localisationNext=${maxPage}'"
						value="LastPage" />

				<!--             <button type="button" class="btn btn-default">50</button> -->
				<!--             <button type="button" class="btn btn-default">100</button> -->
			</div>
		</div>
    </footer>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/dashboard.js"></script>

</body>
</html>